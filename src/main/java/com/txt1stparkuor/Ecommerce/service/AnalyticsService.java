package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LowStockRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesSummaryRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesTrendRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.TopProductsRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.DailySalesTrendResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.LowStockProductResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.PendingOrdersCountResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryComparison;
import com.txt1stparkuor.Ecommerce.domain.dto.response.TopProductResponse;

import java.util.List;

public interface AnalyticsService {

    SalesSummaryComparison getSalesSummary(SalesSummaryRequest request);

    PendingOrdersCountResponse getPendingOrdersCount();

    List<DailySalesTrendResponse> getSalesTrend(SalesTrendRequest request);

    List<TopProductResponse> getTopSellingProducts(TopProductsRequest request);

    PaginationResponseDto<LowStockProductResponse> getLowStockProducts(LowStockRequest request);
}
