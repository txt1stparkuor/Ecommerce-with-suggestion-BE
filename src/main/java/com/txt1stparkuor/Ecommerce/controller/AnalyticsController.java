package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LowStockRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesSummaryRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesTrendRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.TopProductsRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.DailySalesTrendResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.PendingOrdersCountResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryComparison;
import com.txt1stparkuor.Ecommerce.domain.dto.response.TopProductResponse;
import com.txt1stparkuor.Ecommerce.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Analytics Management", description = "Operations related to administrative analytics and metrics")
@ApiCommonResponses
public class AnalyticsController {

    AnalyticsService analyticsService;

    @Operation(
            summary = "Get sales summary with period-over-period comparison",
            description = "Retrieves sales metrics including total revenue, total orders, and average order value (AOV) for both current and previous periods of equal duration, along with computed change percentages and deltas. Cached with a 5-minute TTL. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sales summary comparison",
                    content = @Content(schema = @Schema(implementation = SalesSummaryComparison.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: 'from' after 'to', range exceeds 1 year, or invalid date format")
    })
    @GetMapping(UrlConstant.Analytics.SALES_SUMMARY)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSalesSummary(@Valid SalesSummaryRequest request) {
        return VsResponseUtil.success(analyticsService.getSalesSummary(request));
    }

    @Operation(
            summary = "Get count of pending orders",
            description = "Retrieves the real-time count of orders currently in PENDING status. Not cached. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pending orders count",
                    content = @Content(schema = @Schema(implementation = PendingOrdersCountResponse.class)))
    })
    @GetMapping(UrlConstant.Analytics.PENDING_ORDERS_COUNT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPendingOrdersCount() {
        return VsResponseUtil.success(analyticsService.getPendingOrdersCount());
    }

    @Operation(
            summary = "Get daily sales trend chart data",
            description = "Retrieves daily revenue and order counts for a given date range (max 90 days). Cached by date range. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sales trend data",
                    content = @Content(schema = @Schema(implementation = DailySalesTrendResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: 'from' after 'to', range exceeds 90 days, or invalid date format")
    })
    @GetMapping(UrlConstant.Analytics.SALES_TREND)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSalesTrend(@Valid SalesTrendRequest request) {
        return VsResponseUtil.success(analyticsService.getSalesTrend(request));
    }

    @Operation(
            summary = "Get top selling products leaderboard",
            description = "Retrieves a fixed top-N leaderboard of products ranked by units sold and revenue within the last N days (default 30, max 90). Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved top selling products leaderboard",
                    content = @Content(schema = @Schema(implementation = TopProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid limit (1-100) or invalid days (1-90)")
    })
    @GetMapping(UrlConstant.Analytics.TOP_PRODUCTS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTopSellingProducts(@Valid TopProductsRequest request) {
        return VsResponseUtil.success(analyticsService.getTopSellingProducts(request));
    }

    @Operation(
            summary = "Get low stock inventory alerts",
            description = "Retrieves a paginated list of products whose stock is below or equal to the specified threshold (default 10). Ordered by stock quantity ascending. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved low stock products",
                    content = @Content(schema = @Schema(implementation = PaginationResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid pagination parameters or invalid threshold")
    })
    @GetMapping(UrlConstant.Analytics.LOW_STOCK)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLowStockProducts(@Valid LowStockRequest request) {
        return VsResponseUtil.success(analyticsService.getLowStockProducts(request));
    }
}
