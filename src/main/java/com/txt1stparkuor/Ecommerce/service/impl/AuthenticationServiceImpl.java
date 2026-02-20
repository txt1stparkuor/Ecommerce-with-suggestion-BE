package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LoginRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RefreshTokenRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.AuthenticationResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import com.txt1stparkuor.Ecommerce.exception.InvalidException;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.exception.UnauthorizedException;
import com.txt1stparkuor.Ecommerce.repository.UserRepository;
import com.txt1stparkuor.Ecommerce.security.UserPrincipal;
import com.txt1stparkuor.Ecommerce.security.jwt.JwtTokenProvider;
import com.txt1stparkuor.Ecommerce.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.generateToken(userPrincipal, false);
            String refreshToken = jwtTokenProvider.generateToken(userPrincipal, true);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .id(userPrincipal.getId())
                    .authorities(userPrincipal.getAuthorities())
                    .build();
        } catch (InternalAuthenticationServiceException | BadCredentialsException ex) {
            throw new UnauthorizedException(ErrorMessage.Auth.ERR_INCORRECT_CREDENTIALS);
        }

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new InvalidException(ErrorMessage.Auth.ERR_INVALID_REFRESH_TOKEN);
        }

        String username = jwtTokenProvider.getUsernameFromToken(request.getRefreshToken());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.USERNAME_NOT_FOUND));

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        String newAccessToken = jwtTokenProvider.generateToken(userPrincipal, false);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .id(userPrincipal.getId())
                .authorities(userPrincipal.getAuthorities())
                .build();
    }
}
