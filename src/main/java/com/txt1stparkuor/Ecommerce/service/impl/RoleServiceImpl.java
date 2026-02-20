package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.request.RoleRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RoleResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Role;
import com.txt1stparkuor.Ecommerce.domain.mapper.RoleMapper;
import com.txt1stparkuor.Ecommerce.exception.DuplicateResourceException;
import com.txt1stparkuor.Ecommerce.repository.RoleRepository;
import com.txt1stparkuor.Ecommerce.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {
        roleRepository.findByName(request.getName()).ifPresent(role -> {
            throw new DuplicateResourceException(ErrorMessage.ERR_DUPLICATE, new String[]{"role", request.getName()});
        });

        Role role = roleMapper.toRole(request);

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }
}
