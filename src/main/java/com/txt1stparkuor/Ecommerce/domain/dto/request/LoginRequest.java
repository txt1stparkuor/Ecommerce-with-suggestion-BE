package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @Schema(description = "Username of the user", example = "user123")
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String username;

    @Schema(description = "Password of the user", example = "password123")
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String password;
}
