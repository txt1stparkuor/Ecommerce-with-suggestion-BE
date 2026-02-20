package com.txt1stparkuor.Ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserDetails loadUserById(String id);
}
