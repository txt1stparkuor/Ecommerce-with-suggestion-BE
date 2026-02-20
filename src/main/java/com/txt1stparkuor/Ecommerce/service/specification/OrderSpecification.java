package com.txt1stparkuor.Ecommerce.service.specification;

import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.entity.Order;
import com.txt1stparkuor.Ecommerce.domain.entity.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class OrderSpecification {

    public Specification<Order> filter(OrderFilterRequest filterDto) {
        return Specification.where(hasStatus(filterDto.getStatus()))
                .and(hasUserId(filterDto.getUserId()))
                .and(hasKeyword(filterDto.getKeyword()));
    }

    private Specification<Order> hasStatus(OrderStatus status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Specification<Order> hasUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(userId)) {
                return criteriaBuilder.equal(root.get("user").get("id"), userId);
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Specification<Order> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(keyword)) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("orderCode")), "%" + keyword.toLowerCase() + "%");
            }
            return criteriaBuilder.conjunction();
        };
    }
}
