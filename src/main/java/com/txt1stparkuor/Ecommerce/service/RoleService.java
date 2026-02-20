package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.request.RoleRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
}
