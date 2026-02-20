package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    private String password;

    @Email(message = ErrorMessage.Validation.INVALID_FORMAT_FIELD)
    private String email;

    private String fullName;

    private String role;
}
