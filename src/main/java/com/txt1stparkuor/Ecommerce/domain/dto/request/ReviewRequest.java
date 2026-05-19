package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request DTO for creating or updating a review")
public class ReviewRequest {

    @NotNull(message = ErrorMessage.Validation.NOT_NULL)
    @Min(value = 0, message = ErrorMessage.Validation.INVALID_SCORE)
    @Max(value = 5, message = ErrorMessage.Validation.INVALID_SCORE)
    @Schema(description = "Rating of the product (0-5)", example = "5")
    private Integer rating;

    @Schema(description = "Comment for the review", example = "Great product, highly recommended!")
    private String comment;
}
