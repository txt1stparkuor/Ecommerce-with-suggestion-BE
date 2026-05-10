package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Management", description = "Operations related to product categories")
@ApiCommonResponses
public class CategoryController {

    CategoryService categoryService;

    @Operation(
            summary = "Get all categories",
            description = "Retrieves a list of all product categories, including their hierarchical structure."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Category.CATEGORY_COMMON)
    public ResponseEntity<?> getAllCategories() {
        return VsResponseUtil.success(categoryService.getAllCategories());
    }

    @Operation(
            summary = "Get all categories with products",
            description = "Retrieves a list of all product categories, each including its associated products."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories with products")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Category.CATEGORIES_WITH_PRODUCTS)
    public ResponseEntity<?> getAllCategoriesWithProducts() {
        return VsResponseUtil.success(categoryService.getAllCategoriesWithProducts());
    }

    @Operation(
            summary = "Get leaf categories",
            description = "Retrieves a list of categories that do not have any child categories. Optionally filters by keyword."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved leaf categories")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Category.LEAF_CATEGORIES)
    public ResponseEntity<?> getLeafCategories(@RequestParam(required = false) String keyword) {
        return VsResponseUtil.success(categoryService.getLeafCategories(keyword));
    }
}