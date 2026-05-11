package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.request.ForgotPasswordRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LoginRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RefreshTokenRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ResetPasswordRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

    void forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
