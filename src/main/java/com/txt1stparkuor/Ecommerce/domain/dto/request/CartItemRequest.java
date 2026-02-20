package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    private String productId;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Min(value = 1, message = ErrorMessage.Validation.POSITIVE)
    private Integer quantity;
}
