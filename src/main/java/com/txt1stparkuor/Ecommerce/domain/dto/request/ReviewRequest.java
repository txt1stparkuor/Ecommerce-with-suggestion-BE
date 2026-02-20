package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import jakarta.validation.constraints.Max;
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
public class ReviewRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Min(value = 0, message = ErrorMessage.Validation.INVALID_SCORE)
    @Max(value = 5, message = ErrorMessage.Validation.INVALID_SCORE)
    private Integer rating;

    private String comment;
}
