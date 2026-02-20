package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RoleRequest;
import com.txt1stparkuor.Ecommerce.service.RoleService;
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
public class RoleController {

    RoleService roleService;

    @PostMapping(UrlConstant.Role.ROLE_COMMON)
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, roleService.createRole(request));
    }
}
