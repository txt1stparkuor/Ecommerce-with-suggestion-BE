package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.constant.SortByDataConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RegisterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.AuthenticationResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.UserResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Role;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import com.txt1stparkuor.Ecommerce.domain.mapper.UserMapper;
import com.txt1stparkuor.Ecommerce.exception.DuplicateResourceException;
import com.txt1stparkuor.Ecommerce.exception.ForbiddenException;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.RoleRepository;
import com.txt1stparkuor.Ecommerce.repository.UserRepository;
import com.txt1stparkuor.Ecommerce.security.UserPrincipal;
import com.txt1stparkuor.Ecommerce.security.jwt.JwtTokenProvider;
import com.txt1stparkuor.Ecommerce.service.UserService;
import com.txt1stparkuor.Ecommerce.service.specification.UserSpecification;
import com.txt1stparkuor.Ecommerce.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final UserSpecification userSpecification;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Username", request.getUsername()});
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Email", request.getEmail()});
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.INVALID_ROLE));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .roles(new HashSet<>(Set.of(userRole)))
                .build();

        userRepository.save(user);

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        String jwtToken = jwtTokenProvider.generateToken(userPrincipal, false);
        String refreshToken = jwtTokenProvider.generateToken(userPrincipal, true);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(userPrincipal.getId())
                .authorities(userPrincipal.getAuthorities())
                .build();
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<UserResponse> getAllUsers(UserFilterRequest filterDto) {
        Pageable pageable = PaginationUtil.buildPageable(filterDto, SortByDataConstant.USER);
        Specification<User> spec = userSpecification.filter(filterDto);

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserResponse> userResponses = userMapper.toListUserResponse(userPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(filterDto, SortByDataConstant.USER, userPage);

        return new PaginationResponseDto<>(meta, userResponses);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !currentUserId.equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        if (StringUtils.hasText(request.getUsername()) && !user.getUsername().equals(request.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Username", request.getUsername()});
            }
        }

        if (StringUtils.hasText(request.getEmail()) && !user.getEmail().equals(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Email", request.getEmail()});
            }
        }

        userMapper.updateUserFromRequest(request, user);

        if (StringUtils.hasText(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (StringUtils.hasText(request.getRole())) {
            if (!isAdmin) {
                throw new ForbiddenException(ErrorMessage.FORBIDDEN);
            }
            Role role = roleRepository.findByName(request.getRole())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.INVALID_ROLE));
            user.setRoles(new HashSet<>(Set.of(role)));
        }

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Username", request.getUsername()});
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"Email", request.getEmail()});
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.INVALID_ROLE));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .fullName(request.getFullName())
                .roles(new HashSet<>(Set.of(role)))
                .build();

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !currentUserId.equals(userId)) {
            throw new ForbiddenException(ErrorMessage.FORBIDDEN);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        return userMapper.toUserResponse(user);
    }
}
