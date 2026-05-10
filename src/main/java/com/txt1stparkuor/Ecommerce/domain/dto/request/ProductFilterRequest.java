package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for filtering products with pagination")
public class ProductFilterRequest extends PaginationFullRequestDto {
    private String categoryId;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Minimum price to filter by", example = "10.0")
    private Double minPrice;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    @Schema(description = "Maximum price to filter by", example = "100.0")
    private Double maxPrice;

    @Min(value = 0, message = ErrorMessage.Validation.INVALID_SCORE)
    @Max(value = 5, message = ErrorMessage.Validation.INVALID_SCORE)
    @Schema(description = "Minimum rating to filter by", example = "4.0")
    private Double minRating;
}
