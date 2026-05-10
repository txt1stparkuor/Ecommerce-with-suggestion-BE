package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UserUpdateRequest;
import com.txt1stparkuor.Ecommerce.service.RecommendationService;
import com.txt1stparkuor.Ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User Management", description = "Operations related to user accounts and profiles")
public class UserController {

    UserService userService;
    RecommendationService recommendationService;

    @Operation(
            summary = "Get current user's information",
            description = "Retrieves the profile information of the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(UrlConstant.User.GET_CURRENT_USER)
    public ResponseEntity<?> getMyInfo() {
        return VsResponseUtil.success(userService.getMyInfo());
    }

    @Operation(
            summary = "Get all users (Admin only)",
            description = "Retrieves a paginated and filterable list of all users. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid filter or pagination parameters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)")
    })
    @GetMapping(UrlConstant.User.USER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers(@Valid UserFilterRequest filterDto) {
        return VsResponseUtil.success(userService.getAllUsers(filterDto));
    }

    @Operation(
            summary = "Update user information",
            description = "Updates the profile information of a user. Can update own profile or any user's profile if ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input data or password format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges to update another user's profile"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Username or email already exists")
    })
    @PutMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request) {
        return VsResponseUtil.success(userService.updateUser(id, request));
    }

    @Operation(
            summary = "Delete user (Admin only)",
            description = "Deletes a user by their unique ID. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping(UrlConstant.User.USER_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return VsResponseUtil.success(HttpStatus.NO_CONTENT, null);
    }

    @Operation(
            summary = "Create a new user (Admin only)",
            description = "Creates a new user with specified details and role. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid input data or role"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)"),
            @ApiResponse(responseCode = "409", description = "Conflict: Username or email already exists")
    })
    @PostMapping(UrlConstant.User.USER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest request) {
        return VsResponseUtil.success(HttpStatus.CREATED, userService.createUser(request));
    }

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves a user's profile information by their unique ID. Accessible by owner or ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges to view another user's profile"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(UrlConstant.User.USER_ID)
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        return VsResponseUtil.success(userService.getUserById(id));
    }

    @Operation(
            summary = "Get user recommendations",
            description = "Retrieves product recommendations tailored for the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User recommendations retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping(UrlConstant.User.USER_RECOMMENDATIONS)
    public ResponseEntity<?> getUserRecommendations(@Valid PaginationRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        return VsResponseUtil.success(recommendationService.getUserRecommendations(userId, request));
    }
}
