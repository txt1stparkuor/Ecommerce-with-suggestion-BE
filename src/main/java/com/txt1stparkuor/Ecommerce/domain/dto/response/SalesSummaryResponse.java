package com.txt1stparkuor.Ecommerce.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Sales summary metrics for dashboard cards")
public class SalesSummaryResponse {

    @Schema(description = "Total revenue across all completed sales", example = "125430.50")
    private BigDecimal totalRevenue;

    @Schema(description = "Total count of completed sales orders", example = "1250")
    private long totalOrders;

    @Schema(description = "Average order value (AOV) calculated as totalRevenue / totalOrders", example = "100.34")
    private BigDecimal averageOrderValue;

    public SalesSummaryResponse(Double totalRevenue, Long totalOrders, Double averageOrderValue) {
        this.totalRevenue = totalRevenue != null
                ? BigDecimal.valueOf(totalRevenue).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.totalOrders = totalOrders != null ? totalOrders : 0L;
        if (this.totalOrders > 0) {
            this.averageOrderValue = this.totalRevenue.divide(BigDecimal.valueOf(this.totalOrders), 2, RoundingMode.HALF_UP);
        } else {
            this.averageOrderValue = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
    }
}
