package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for retrieving top selling products leaderboard")
public class TopProductsRequest {

    @Min(value = 1, message = ErrorMessage.Analytics.ERR_LIMIT_MIN)
    @Max(value = 100, message = ErrorMessage.Analytics.ERR_LIMIT_MAX)
    @Schema(description = "Number of top products to retrieve (1-100)", example = "5", defaultValue = "5")
    @Builder.Default
    private int limit = 5;

    @Min(value = 1, message = ErrorMessage.Analytics.ERR_DAYS_MIN)
    @Max(value = 90, message = ErrorMessage.Analytics.ERR_DAYS_MAX)
    @Schema(description = "Number of past days for analytics (1-90)", example = "30", defaultValue = "30")
    @Builder.Default
    private int days = 30;
}
