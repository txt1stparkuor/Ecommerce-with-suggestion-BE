package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findByProductId(String productId, Pageable pageable);

    Long countByProductId(String productId);
}
