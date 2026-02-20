package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserUpdateRequest;
import com.txt1stparkuor.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getMyInfo() {
        return VsResponseUtil.success(userService.getMyInfo());
    }

    @GetMapping(UrlConstant.User.USER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(@Valid UserFilterRequest filterDto) {
        return VsResponseUtil.success(userService.getAllUsers(filterDto));
    }

    @PutMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        return VsResponseUtil.success(userService.updateUser(id, request));
    }

    @DeleteMapping(UrlConstant.User.USER_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return VsResponseUtil.success(HttpStatus.NO_CONTENT, null);
    }

    @PostMapping(UrlConstant.User.USER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request));
    }

    @GetMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return VsResponseUtil.success(userService.getUserById(id));
    }
}
