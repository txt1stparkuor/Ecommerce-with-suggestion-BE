package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.dto.response.LowStockProductResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.reviews r LEFT JOIN FETCH r.user")
    List<Product> findAllWithReviewsAndUsers();

    // Modified to eagerly fetch category and its parent to reduce N+1 queries for getFullPath()
    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query(value = """
        SELECT new com.txt1stparkuor.Ecommerce.domain.dto.response.LowStockProductResponse(
            p.id,
            p.name,
            COALESCE(p.stockQuantity, 0),
            p.price
        )
        FROM Product p
        WHERE COALESCE(p.stockQuantity, 0) <= :threshold
        ORDER BY COALESCE(p.stockQuantity, 0) ASC, p.name ASC
        """,
        countQuery = """
        SELECT COUNT(p.id)
        FROM Product p
        WHERE COALESCE(p.stockQuantity, 0) <= :threshold
        """)
    Page<LowStockProductResponse> findLowStockProducts(
            @Param("threshold") int threshold,
            Pageable pageable
    );
}