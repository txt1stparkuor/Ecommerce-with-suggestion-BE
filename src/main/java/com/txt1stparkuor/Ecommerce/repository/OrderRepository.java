package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.domain.dto.response.DailySalesTrendResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserId(String userId, Sort sort);
    Optional<Order> findByIdempotencyKeyAndUserId(String idempotencyKey, String userId);
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderDetails od LEFT JOIN FETCH od.product WHERE o.idempotencyKey = :idempotencyKey AND o.user.id = :userId")
    Optional<Order> findByIdempotencyKeyAndUserIdWithDetails(
            @Param("idempotencyKey") String idempotencyKey,
            @Param("userId") String userId);

    @Query("""
        SELECT new com.txt1stparkuor.Ecommerce.domain.dto.response.SalesSummaryResponse(
            COALESCE(SUM(o.totalAmount), 0.0),
            COUNT(o.id),
            COALESCE(AVG(o.totalAmount), 0.0)
        )
        FROM Order o
        WHERE o.createdAt >= :startDate AND o.createdAt < :endDate
          AND o.status IN :completedStatuses
        """)
    SalesSummaryResponse getSalesSummary(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            @Param("completedStatuses") Collection<OrderStatus> completedStatuses
    );

    default SalesSummaryResponse getSalesSummary(Instant startDate, Instant endDate) {
        return getSalesSummary(startDate, endDate, OrderStatus.COMPLETED_SALES);
    }

    long countByStatus(OrderStatus status);

    @Query("""
        SELECT new com.txt1stparkuor.Ecommerce.domain.dto.response.DailySalesTrendResponse(
            FUNCTION('DATE', o.createdAt),
            COALESCE(SUM(o.totalAmount), 0.0),
            COUNT(o.id)
        )
        FROM Order o
        WHERE o.createdAt >= :startDate AND o.createdAt < :endDate
          AND o.status IN :completedStatuses
        GROUP BY FUNCTION('DATE', o.createdAt)
        ORDER BY FUNCTION('DATE', o.createdAt) ASC
        """)
    List<DailySalesTrendResponse> getDailySalesTrend(
            @Param("startDate") Instant startDate,
            @Param("endDate") Instant endDate,
            @Param("completedStatuses") Collection<OrderStatus> completedStatuses
    );
}
