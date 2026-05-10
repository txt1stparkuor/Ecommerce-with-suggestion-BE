package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for updating user information")
public class UserUpdateRequest {

    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    @Schema(description = "Username of the user", example = "user123")
    private String username;

    @Schema(description = "Password of the user", example = "Password123!")
    private String password;

    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Role of the user", example = "USER")
    private String role;
}
