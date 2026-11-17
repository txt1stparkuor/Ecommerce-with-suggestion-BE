package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.CacheName;
import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import com.txt1stparkuor.Ecommerce.domain.dto.request.LowStockRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesSummaryRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.SalesTrendRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.TopProductsRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.DailySalesTrendResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.LowStockProductResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.PendingOrdersCountResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryComparison;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SummaryChange;
import com.txt1stparkuor.Ecommerce.domain.dto.response.TopProductResponse;
import com.txt1stparkuor.Ecommerce.exception.InvalidException;
import com.txt1stparkuor.Ecommerce.repository.OrderDetailRepository;
import com.txt1stparkuor.Ecommerce.repository.OrderRepository;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.service.AnalyticsService;
import com.txt1stparkuor.Ecommerce.util.PaginationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AnalyticsServiceImpl implements AnalyticsService {

    OrderRepository orderRepository;
    OrderDetailRepository orderDetailRepository;
    ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = CacheName.SALES_SUMMARY,
            key = "#request.from.toString() + '_' + #request.to.toString()",
            condition = "#request != null && #request.from != null && #request.to != null"
    )
    public SalesSummaryComparison getSalesSummary(SalesSummaryRequest request) {
        LocalDate from = request.getFrom();
        LocalDate to = request.getTo();
        if (from.isAfter(to)) {
            throw new InvalidException(ErrorMessage.Analytics.ERR_DATE_RANGE_INVALID);
        }
        if (from.plusYears(1).isBefore(to)) {
            throw new InvalidException(ErrorMessage.Analytics.ERR_DATE_RANGE_EXCEEDED_YEAR);
        }

        long days = ChronoUnit.DAYS.between(from, to) + 1;
        LocalDate prevTo = from.minusDays(1);
        LocalDate prevFrom = from.minusDays(days);

        Instant currentStartDate = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant currentEndDate = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant prevStartDate = prevFrom.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant prevEndDate = currentStartDate;

        SalesSummaryResponse current = getSummaryForPeriod(currentStartDate, currentEndDate);
        SalesSummaryResponse previous = getSummaryForPeriod(prevStartDate, prevEndDate);
        SummaryChange change = calculateSummaryChange(current, previous);

        return new SalesSummaryComparison(current, previous, change);
    }

    private SalesSummaryResponse getSummaryForPeriod(Instant startDate, Instant endDate) {
        SalesSummaryResponse summary = orderRepository.getSalesSummary(
                startDate,
                endDate,
                OrderStatus.COMPLETED_SALES
        );
        if (summary == null) {
            return SalesSummaryResponse.builder()
                    .totalRevenue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                    .totalOrders(0L)
                    .averageOrderValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                    .build();
        }
        if (summary.getTotalRevenue() == null) {
            summary.setTotalRevenue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        }
        if (summary.getAverageOrderValue() == null) {
            summary.setAverageOrderValue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        }
        return summary;
    }

    private SummaryChange calculateSummaryChange(SalesSummaryResponse current, SalesSummaryResponse previous) {
        BigDecimal revenuePct = calculatePercentageChange(
                current.getTotalRevenue(),
                previous.getTotalRevenue()
        );

        BigDecimal ordersPct = calculatePercentageChange(
                BigDecimal.valueOf(current.getTotalOrders()),
                BigDecimal.valueOf(previous.getTotalOrders())
        );

        BigDecimal currentAov = current.getAverageOrderValue() != null
                ? current.getAverageOrderValue()
                : BigDecimal.ZERO;
        BigDecimal previousAov = previous.getAverageOrderValue() != null
                ? previous.getAverageOrderValue()
                : BigDecimal.ZERO;
        BigDecimal aovDelta = currentAov.subtract(previousAov).setScale(2, RoundingMode.HALF_UP);

        return new SummaryChange(revenuePct, ordersPct, aovDelta);
    }

    private BigDecimal calculatePercentageChange(BigDecimal current, BigDecimal previous) {
        BigDecimal cur = current != null ? current : BigDecimal.ZERO;
        BigDecimal prev = previous != null ? previous : BigDecimal.ZERO;

        if (prev.compareTo(BigDecimal.ZERO) == 0) {
            if (cur.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
            return BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP);
        }

        return cur.subtract(prev)
                .multiply(BigDecimal.valueOf(100))
                .divide(prev, 2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional(readOnly = true)
    public PendingOrdersCountResponse getPendingOrdersCount() {
        long count = orderRepository.countByStatus(OrderStatus.PENDING);
        return new PendingOrdersCountResponse(count);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = CacheName.SALES_TREND,
            key = "#request.from.toString() + '_' + #request.to.toString()",
            condition = "#request != null && #request.from != null && #request.to != null"
    )
    public List<DailySalesTrendResponse> getSalesTrend(SalesTrendRequest request) {
        LocalDate from = request.getFrom();
        LocalDate to = request.getTo();
        if (from.isAfter(to)) {
            throw new InvalidException(ErrorMessage.Analytics.ERR_DATE_RANGE_INVALID);
        }
        long daysBetween = ChronoUnit.DAYS.between(from, to);
        if (daysBetween > 90) {
            throw new InvalidException(ErrorMessage.Analytics.ERR_DATE_RANGE_EXCEEDED);
        }

        Instant startDate = from.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant endDate = to.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        List<DailySalesTrendResponse> rawTrends = orderRepository.getDailySalesTrend(
                startDate,
                endDate,
                OrderStatus.COMPLETED_SALES
        );

        Map<LocalDate, DailySalesTrendResponse> trendMap = rawTrends.stream()
                .filter(t -> t.getDate() != null)
                .collect(Collectors.toMap(
                        DailySalesTrendResponse::getDate,
                        t -> t,
                        (existing, replacement) -> existing
                ));

        List<DailySalesTrendResponse> filledTrends = new ArrayList<>();
        for (LocalDate curr = from; !curr.isAfter(to); curr = curr.plusDays(1)) {
            DailySalesTrendResponse point = trendMap.get(curr);
            if (point != null) {
                filledTrends.add(point);
            } else {
                filledTrends.add(DailySalesTrendResponse.builder()
                        .date(curr)
                        .revenue(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                        .orderCount(0L)
                        .build());
            }
        }

        return filledTrends;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(
            value = CacheName.TOP_PRODUCTS,
            key = "(#request != null ? #request.limit : 5) + '_' + (#request != null ? #request.days : 30)"
    )
    public List<TopProductResponse> getTopSellingProducts(TopProductsRequest request) {
        TopProductsRequest effectiveRequest = request != null ? request : new TopProductsRequest();
        int limit = effectiveRequest.getLimit() > 0 ? effectiveRequest.getLimit() : 5;
        Instant startDate = Instant.now().truncatedTo(ChronoUnit.HOURS).minus(effectiveRequest.getDays(), ChronoUnit.DAYS);

        Pageable pageable = PageRequest.of(0, limit);
        return orderDetailRepository.getTopSellingProducts(
                startDate,
                OrderStatus.COMPLETED_SALES,
                pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<LowStockProductResponse> getLowStockProducts(LowStockRequest request) {
        LowStockRequest effectiveRequest = request != null ? request : new LowStockRequest();
        Pageable pageable = PaginationUtil.buildPageable(effectiveRequest);

        Page<LowStockProductResponse> productPage = productRepository.findLowStockProducts(
                effectiveRequest.getThreshold(),
                pageable
        );

        PagingMeta meta = PaginationUtil.buildPagingMeta(effectiveRequest, productPage);

        return new PaginationResponseDto<>(meta, productPage.getContent());
    }
}
