package com.txt1stparkuor.Ecommerce.constant;

import lombok.Getter;

import java.util.Optional;
import java.util.Set;

@Getter
public enum SortByDataConstant {
    USER(
            Set.of("createdAt")
    ),
    PRODUCT(
            Set.of("name", "price","averageRating")
    ),
    REVIEW(
            Set.of("createdAt", "rating")
    ),
    ORDER(
            Set.of("createdAt", "totalAmount")
    );

    private final Set<String> allowedFields;
    private final String defaultSortField;

    SortByDataConstant(Set<String> allowedFields, String defaultSortField) {
        this.allowedFields = allowedFields;
        this.defaultSortField = defaultSortField;
    }

    SortByDataConstant(Set<String> allowedFields) {
        this.allowedFields = allowedFields;
        this.defaultSortField = null;
    }

    public Optional<String> resolveSortField(String clientSortBy) {
        if (clientSortBy != null && !clientSortBy.isBlank()) {
            if (this.allowedFields.contains(clientSortBy)) {
                return Optional.of(clientSortBy);
            } else {
                throw new IllegalArgumentException(
                        String.format(ErrorMessage.INVALID_SORT_FIELD, clientSortBy, this.allowedFields)
                );
            }
        }
        return Optional.ofNullable(this.defaultSortField);
    }
}
