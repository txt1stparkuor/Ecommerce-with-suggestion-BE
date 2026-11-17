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
@Schema(description = "Sales summary with period-over-period comparison")
public class SalesSummaryComparison {

    @Schema(description = "Sales summary metrics for the current period")
    private SalesSummaryResponse current;

    @Schema(description = "Sales summary metrics for the previous period of equal duration")
    private SalesSummaryResponse previous;

    @Schema(description = "Computed changes between current and previous period")
    private SummaryChange change;
}
