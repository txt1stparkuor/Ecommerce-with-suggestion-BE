package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LoginRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RefreshTokenRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RegisterRequest;
import com.txt1stparkuor.Ecommerce.service.AuthenticationService;
import com.txt1stparkuor.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    UserService userService;
    AuthenticationService authenticationService;

    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.register(request));
    }

    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return VsResponseUtil.success(authenticationService.login(request));
    }

    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return VsResponseUtil.success(authenticationService.refreshToken(request));
    }
}
