package com.txt1stparkuor.Ecommerce.util;

import com.txt1stparkuor.Ecommerce.constant.CommonConstant;
import com.txt1stparkuor.Ecommerce.constant.SortByDataConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationSortRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class PaginationUtil {
    public static Pageable buildPageable(PaginationRequestDto request) {
        return PageRequest.of(request.getPageNum(), request.getPageSize());
    }

    public static Pageable buildPageable(PaginationSortRequestDto request, SortByDataConstant constant) {
        Optional<String> sortFieldOptional = constant.resolveSortField(request.getSortBy());
        Sort sort;
        if (sortFieldOptional.isPresent()) {
            String sortByField = sortFieldOptional.get();
            sort = Sort.by(sortByField);
            if (request.getIsAscending() != null && request.getIsAscending()) {
                sort = sort.ascending();
            } else {
                sort = sort.descending();
            }
        } else {
            sort = Sort.unsorted();
        }
        return PageRequest.of(request.getPageNum(), request.getPageSize(), sort);
    }

    public static Sort buildSort(PaginationSortRequestDto request, SortByDataConstant constant) {
        Optional<String> sortFieldOptional = constant.resolveSortField(request.getSortBy());
        return sortFieldOptional
                .map(field -> {
                    Sort.Direction direction = Boolean.TRUE.equals(request.getIsAscending())
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC;
                    return Sort.by(direction, field);
                })
                .orElse(Sort.unsorted());
    }

    public static <T> PagingMeta buildPagingMeta(PaginationRequestDto request, Page<T> page) {
        return new PagingMeta(
                page.getTotalElements(),
                page.getTotalPages(),
                request.getPageNum() + 1,
                request.getPageSize(),
                null,
                null
        );
    }

    public static <T> PagingMeta buildPagingMeta(PaginationSortRequestDto request, SortByDataConstant constant, Page<T> pages) {
        Optional<String> sortFieldOptional = constant.resolveSortField(request.getSortBy());
        String finalSortBy = sortFieldOptional.orElse(null);
        String finalSortDirection = null;
        if (finalSortBy != null) {
            finalSortDirection = Boolean.TRUE.equals(request.getIsAscending())
                    ? CommonConstant.SORT_TYPE_ASC
                    : CommonConstant.SORT_TYPE_DESC;
        }
        return new PagingMeta(
                pages.getTotalElements(),
                pages.getTotalPages(),
                request.getPageNum() + 1,
                request.getPageSize(),
                finalSortBy,
                finalSortDirection
        );
    }
}
