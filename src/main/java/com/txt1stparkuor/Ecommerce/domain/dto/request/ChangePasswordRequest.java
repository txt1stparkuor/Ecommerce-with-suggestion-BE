package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Request DTO for changing password")
public class ChangePasswordRequest {
    @Size(min = 6, max = 100, message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
            message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD
    )
    private String oldPassword;
    @Size(min = 6, max = 100, message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD)
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
            message = ErrorMessage.Validation.INVALID_FORMAT_PASSWORD
    )
    private String newPassword;
}
