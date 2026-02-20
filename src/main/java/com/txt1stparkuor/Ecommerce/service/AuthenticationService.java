package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.request.LoginRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RefreshTokenRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);
}
