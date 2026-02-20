package com.txt1stparkuor.Ecommerce.service.specification;

import com.txt1stparkuor.Ecommerce.domain.dto.request.UserFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserSpecification {

    public Specification<User> filter(UserFilterRequest filterDto) {
        return Specification.where(hasKeyword(filterDto.getKeyword()))
                .and(hasRole(filterDto.getRole()));
    }

    private Specification<User> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(keyword)) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), likePattern)
                );
            }
            return criteriaBuilder.conjunction();
        };
    }

    private Specification<User> hasRole(String roleName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.hasText(roleName)) {
                return criteriaBuilder.equal(root.join("roles").get("name"), roleName);
            }
            return criteriaBuilder.conjunction();
        };
    }
}
