package com.txt1stparkuor.Ecommerce.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Low stock product alert response item for analytics dashboard")
public class LowStockProductResponse {

    @Schema(description = "Product unique identifier", example = "prod-abc-123")
    private String productId;

    @Schema(description = "Product name", example = "Wireless Keyboard")
    private String productName;

    @Schema(description = "Current stock quantity", example = "3")
    private Integer stockQuantity;

    @Schema(description = "Product price", example = "49.99")
    private Double price;
}
