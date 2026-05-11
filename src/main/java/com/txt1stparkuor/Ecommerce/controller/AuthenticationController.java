package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.*;
import com.txt1stparkuor.Ecommerce.service.AuthenticationService;
import com.txt1stparkuor.Ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "User authentication and authorization operations")
public class AuthenticationController {
    UserService userService;
    AuthenticationService authenticationService;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with the provided details (username, email, password)."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., validation errors)"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    @SecurityRequirements()
    @PostMapping(UrlConstant.Auth.REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.register(request));
    }

    @Operation(
            summary = "User login",
            description = "Authenticates a user with username/email and password, returning JWT access and refresh tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully, returns JWT tokens"),
            @ApiResponse(responseCode = "400", description = "Invalid login credentials or input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Invalid username or password")
    })
    @SecurityRequirements()
    @PostMapping(UrlConstant.Auth.LOGIN)
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return VsResponseUtil.success(authenticationService.login(request));
    }

    @Operation(
            summary = "Refresh JWT token",
            description = "Refreshes an expired access token using a valid refresh token to obtain new access and refresh tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid refresh token format or missing token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Refresh token is invalid or expired")
    })
    @SecurityRequirements()
    @PostMapping(UrlConstant.Auth.REFRESH_TOKEN)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return VsResponseUtil.success(authenticationService.refreshToken(request));
    }

    @PostMapping(UrlConstant.Auth.FORGOT_PASSWORD)
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authenticationService.forgotPassword(request);
        return VsResponseUtil.success("If an account exists, a reset link has been sent.");
    }

    @PostMapping(UrlConstant.Auth.RESET_PASSWORD)
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return VsResponseUtil.success("Password has been reset.");
    }
}