package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request DTO for adding or updating a cart item")
public class CartItemRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Schema(description = "ID of the product to add to the cart", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private String productId;

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Min(value = 1, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Quantity of the product to add", example = "2")
    private Integer quantity;
}