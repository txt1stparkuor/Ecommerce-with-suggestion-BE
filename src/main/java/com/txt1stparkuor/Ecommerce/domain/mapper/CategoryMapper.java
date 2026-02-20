package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.response.CategoryResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toListCategoryResponse(List<Category> categories);
}
