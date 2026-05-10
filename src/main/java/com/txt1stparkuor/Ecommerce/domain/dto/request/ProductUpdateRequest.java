package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for updating a product")
public class ProductUpdateRequest {

    @Schema(description = "Name of the product", example = "Smartphone XYZ")
    private String name;

    @Schema(description = "Description of the product", example = "A high-end smartphone with 128GB storage.")
    private String description;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Price of the product", example = "999.99")
    private Double price;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Original price of the product before discount", example = "1199.99")
    private Double originalPrice;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Stock quantity of the product", example = "100")
    private Integer stockQuantity;

    @Schema(description = "ID of the category of the product", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private String categoryId;
}
