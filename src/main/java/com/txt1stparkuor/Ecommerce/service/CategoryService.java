package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CategoryFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();

    List<CategoryResponse> getAllCategoriesWithProducts();

    List<CategoryResponse> getLeafCategories(String keyword);
}
