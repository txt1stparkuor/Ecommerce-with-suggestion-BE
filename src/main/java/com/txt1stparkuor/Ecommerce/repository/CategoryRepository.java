package com.txt1stparkuor.Ecommerce.repository;

import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("SELECT c FROM Category c WHERE SIZE(c.products) > 0")
    List<Category> findAllWithProducts();
}
