package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.domain.dto.response.TopProductResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.OrderDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    @Query("""
        SELECT new com.txt1stparkuor.Ecommerce.domain.dto.response.TopProductResponse(
            p.id,
            p.name,
            COALESCE(SUM(od.quantity), 0L),
            COALESCE(SUM(od.price * od.quantity), 0.0)
        )
        FROM OrderDetail od
        JOIN od.order o
        JOIN od.product p
        WHERE o.status IN :validStatuses
          AND o.createdAt >= :startDate
        GROUP BY p.id, p.name
        ORDER BY SUM(od.quantity) DESC
        """)
    List<TopProductResponse> getTopSellingProducts(
            @Param("startDate") Instant startDate,
            @Param("validStatuses") Collection<OrderStatus> validStatuses,
            Pageable pageable
    );
}
