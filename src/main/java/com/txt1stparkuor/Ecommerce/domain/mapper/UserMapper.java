package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.request.UserUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.UserResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Role;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse toUserResponse(User user);

    List<UserResponse> toListUserResponse(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
