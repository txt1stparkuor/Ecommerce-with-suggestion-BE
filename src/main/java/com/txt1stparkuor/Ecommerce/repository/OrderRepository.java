package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

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
}
