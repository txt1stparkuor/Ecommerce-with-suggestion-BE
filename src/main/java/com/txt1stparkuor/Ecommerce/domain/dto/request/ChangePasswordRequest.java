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
@Schema(description = "Request DTO for changing password")
public class ChangePasswordRequest {
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String oldPassword;
    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String newPassword;
}
