package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.UserRepository;
import com.txt1stparkuor.Ecommerce.security.UserPrincipal;
import com.txt1stparkuor.Ecommerce.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ErrorMessage.User.ERR_NOT_FOUND_USERNAME, username)
                ));
        return UserPrincipal.create(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        ErrorMessage.User.ERR_NOT_FOUND_ID,
                        new String[]{id}
                ));
        return UserPrincipal.create(user);
    }
}