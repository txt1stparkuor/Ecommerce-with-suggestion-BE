package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for creating an order")
public class OrderRequest {

    @NotBlank(message = ErrorMessage.Validation.NOT_BLANK)
    @Schema(description = "Shipping address for the order", example = "123 Main St, City, Country")
    private String shippingAddress;

    @NotEmpty(message = ErrorMessage.Validation.NOT_EMPTY)
    @Schema(description = "List of cart item IDs for the order")
    private List<String> cartItemIds;
}
