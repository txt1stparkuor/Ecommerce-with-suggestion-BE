package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
