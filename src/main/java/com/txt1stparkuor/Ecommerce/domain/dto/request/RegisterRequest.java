package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO for user registration")
public class RegisterRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Schema(description = "Username of the user", example = "user123")
    private String username;

    @Size(min = 6, max = 100, message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
            message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD
    )
    @Schema(description = "Password of the user", example = "password123")
    private String password;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    @Schema(description = "Email address of the user", example = "user@example.com")
    private String email;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;
}
