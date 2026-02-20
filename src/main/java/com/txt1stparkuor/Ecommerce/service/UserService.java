package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.request.*;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.AuthenticationResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.UserResponse;

public interface UserService {
    AuthenticationResponse register(RegisterRequest request);

    UserResponse getMyInfo();

    PaginationResponseDto<UserResponse> getAllUsers(UserFilterRequest filterDto);

    UserResponse updateUser(String userId, UserUpdateRequest request);

    void deleteUser(String userId);

    UserResponse createUser(UserCreationRequest request);

    UserResponse getUserById(String userId);
}
