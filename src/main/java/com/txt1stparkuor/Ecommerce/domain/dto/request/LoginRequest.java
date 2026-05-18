package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request DTO for user login")
public class LoginRequest {

    @Schema(description = "Email of the user", example = "user@example.com")
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    private String email;

    @Schema(description = "Password of the user", example = "password123")
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Size(max = 128)
    private String password;
}
