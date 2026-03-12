package com.txt1stparkuor.Ecommerce.service.specification;

import com.txt1stparkuor.Ecommerce.domain.dto.request.CategoryFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CategorySpecification {

    public Specification<Category> filter(CategoryFilterRequest filterDto) {
        // Start with the mandatory condition: must be a leaf category.
        Specification<Category> spec = Specification.where(isLeafCategory());

        // Add the keyword search condition if a keyword is provided.
        if (StringUtils.hasText(filterDto.getKeyword())) {
            spec = spec.and(hasNameLike(filterDto.getKeyword()));
        }

        return spec;
    }

    /**
     * Creates a Specification to find categories with no children.
     */
    private Specification<Category> isLeafCategory() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isEmpty(root.get("children"));
    }

    /**
     * Creates a Specification to find categories where the name contains the given keyword (case-insensitive).
     */
    private Specification<Category> hasNameLike(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}
