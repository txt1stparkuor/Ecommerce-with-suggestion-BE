package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.domain.dto.request.CategoryFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.CategoryResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import com.txt1stparkuor.Ecommerce.domain.mapper.CategoryMapper;
import com.txt1stparkuor.Ecommerce.repository.CategoryRepository;
import com.txt1stparkuor.Ecommerce.service.CategoryService;
import com.txt1stparkuor.Ecommerce.service.specification.CategorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategorySpecification categorySpecification;


    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toListCategoryResponse(categoryRepository.findByParentIsNull());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategoriesWithProducts() {
        return categoryMapper.toListCategoryResponse(categoryRepository.findAllWithProducts());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getLeafCategories(String keyword) {
        CategoryFilterRequest filterDto = new CategoryFilterRequest();
        filterDto.setKeyword(keyword);
        Specification<Category> spec = categorySpecification.filter(filterDto);
        List<Category> categories = categoryRepository.findAll(spec);

        return categoryMapper.toListCategoryResponse(categories);
    }
}
