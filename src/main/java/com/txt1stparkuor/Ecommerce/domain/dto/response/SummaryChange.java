package com.txt1stparkuor.Ecommerce.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Period-over-period percentage and delta changes for sales metrics")
public class SummaryChange {

    @Schema(description = "Percentage change in total revenue compared to previous period", example = "15.50")
    private BigDecimal revenuePct;

    @Schema(description = "Percentage change in order volume compared to previous period", example = "-5.25")
    private BigDecimal ordersPct;

    @Schema(description = "Absolute difference in average order value (AOV) compared to previous period", example = "12.75")
    private BigDecimal aovDelta;
}
