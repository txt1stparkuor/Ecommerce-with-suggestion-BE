package com.txt1stparkuor.Ecommerce.service.specification;

import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductFilterRequest;
import com.txt1stparkuor.Ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSpecification {

    private final CategoryRepository categoryRepository;

    public Specification<Product> filter(ProductFilterRequest filterDto) {
        return Specification.where(hasCategoryId(filterDto.getCategoryId()))
                .and(hasKeyword(filterDto.getKeyword()))
                .and(hasPriceBetween(filterDto.getMinPrice(), filterDto.getMaxPrice()))
                .and(hasMinRating(filterDto.getMinRating()));
    }

    private Specification<Product> hasCategoryId(String categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(categoryId)) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    List<String> categoryIds = new ArrayList<>();
                    collectCategoryIds(category, categoryIds);
                    return root.get("category").get("id").in(categoryIds);
                }
                return criteriaBuilder.disjunction();
            }
            return criteriaBuilder.conjunction();
        };
    }

    private void collectCategoryIds(Category category, List<String> ids) {
        ids.add(category.getId().toString());
        if (category.getChildren() != null) {
            for (Category child : category.getChildren()) {
                collectCategoryIds(child, ids);
            }
        }
    }

    private Specification<Product> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(keyword)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Specification<Product> hasPriceBetween(Double minPrice, Double maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            }
            if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            }
            if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Specification<Product> hasMinRating(Double minRating) {
        return (root, query, criteriaBuilder) -> {
            if (minRating != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), minRating);
            }
            return criteriaBuilder.conjunction();
        };
    }
}
