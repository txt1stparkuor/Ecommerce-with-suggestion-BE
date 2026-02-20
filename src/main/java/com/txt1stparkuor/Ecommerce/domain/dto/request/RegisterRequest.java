package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
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
public class RegisterRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String username;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String password;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    private String email;

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String fullName;
}
