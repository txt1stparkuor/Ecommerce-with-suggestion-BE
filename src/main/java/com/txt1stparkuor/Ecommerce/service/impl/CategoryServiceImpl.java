package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.domain.dto.response.CategoryResponse;
import com.txt1stparkuor.Ecommerce.domain.mapper.CategoryMapper;
import com.txt1stparkuor.Ecommerce.repository.CategoryRepository;
import com.txt1stparkuor.Ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toListCategoryResponse(categoryRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategoriesWithProducts() {
        return categoryMapper.toListCategoryResponse(categoryRepository.findAllWithProducts());
    }
}
