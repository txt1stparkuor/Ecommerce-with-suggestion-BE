package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for updating the quantity of an item in the cart")
public class CartItemUpdateRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Min(value = 1, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "New quantity for the cart item", example = "3")
    private Integer quantity;
}