package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService categoryService;

    @GetMapping(UrlConstant.Category.CATEGORY_COMMON)
    public ResponseEntity<?> getAllCategories() {
        return VsResponseUtil.success(categoryService.getAllCategories());
    }

    @GetMapping(UrlConstant.Category.CATEGORIES_WITH_PRODUCTS)
    public ResponseEntity<?> getAllCategoriesWithProducts() {
        return VsResponseUtil.success(categoryService.getAllCategoriesWithProducts());
    }
}
