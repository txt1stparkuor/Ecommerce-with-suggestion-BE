package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    private String name;

    private String description;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    private Double price;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    private Double originalPrice;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    private Integer stockQuantity;

    private String categoryId;
}
