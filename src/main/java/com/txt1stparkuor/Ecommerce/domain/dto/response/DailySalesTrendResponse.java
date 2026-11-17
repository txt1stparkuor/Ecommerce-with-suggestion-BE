package com.txt1stparkuor.Ecommerce.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Daily sales trend data point for chart visualization")
public class DailySalesTrendResponse {

    @Schema(description = "Date of the sales trend data point (YYYY-MM-DD)", example = "2026-10-01")
    private LocalDate date;

    @Schema(description = "Total revenue for the day from valid (non-cancelled) orders", example = "4520.50")
    private BigDecimal revenue;

    @Schema(description = "Total number of valid (non-cancelled) orders placed on this day", example = "42")
    private long orderCount;

    /**
     * JPQL constructor expression constructor.
     * Handles flexible date conversions from SQL (java.sql.Date, java.time.LocalDate, etc.)
     * and maps revenue to 2-decimal scale BigDecimal.
     */
    public DailySalesTrendResponse(Object date, Double revenue, Long orderCount) {
        if (date instanceof LocalDate ld) {
            this.date = ld;
        } else if (date instanceof java.sql.Date sd) {
            this.date = sd.toLocalDate();
        } else if (date != null) {
            this.date = LocalDate.parse(date.toString());
        }
        this.revenue = revenue != null
                ? BigDecimal.valueOf(revenue).setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.orderCount = orderCount != null ? orderCount : 0L;
    }
}
