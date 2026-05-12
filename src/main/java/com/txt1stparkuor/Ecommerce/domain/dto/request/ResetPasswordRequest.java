package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for resetting password")
public class ResetPasswordRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String token;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    String newPassword;
}
