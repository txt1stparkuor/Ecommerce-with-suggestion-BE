package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.request.RoleRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RoleResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);

    Role toRole(RoleRequest request);
}
