package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
