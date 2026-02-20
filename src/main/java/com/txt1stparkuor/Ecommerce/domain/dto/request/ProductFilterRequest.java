package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
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
public class ProductFilterRequest extends PaginationFullRequestDto {
    private String categoryId;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    private Double minPrice;

    @Min(value = 0, message = ErrorMessage.Validation.POSITIVE)
    private Double maxPrice;

    @Min(value = 0, message = ErrorMessage.Validation.INVALID_SCORE)
    @Max(value = 5, message = ErrorMessage.Validation.INVALID_SCORE)
    private Double minRating;
}
