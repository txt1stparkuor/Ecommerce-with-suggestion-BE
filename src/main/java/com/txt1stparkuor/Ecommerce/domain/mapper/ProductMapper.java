package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.ProductResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryPath", expression = "java(product.getCategory() != null ? product.getCategory().getFullPath() : null)")
    @Mapping(target = "discountPercentage", expression = "java(calculateDiscount(product))")
    @Mapping(target = "categoryId", expression = "java(product.getCategory() != null ? product.getCategory().getId() : null)")
    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toListProductResponse(List<Product> products);

    @Mapping(target = "averageRating", constant = "0.0")
    @Mapping(target = "ratingCount", constant = "0")
    Product toProduct(ProductCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromRequest(ProductUpdateRequest request, @MappingTarget Product product);

    default Double calculateDiscount(Product product) {
        if (product.getOriginalPrice() != null && product.getPrice() != null && product.getOriginalPrice() > 0) {
            double discount = ((product.getOriginalPrice() - product.getPrice()) / product.getOriginalPrice()) * 100;
            return (double) Math.round(discount);
        }
        return 0.0;
    }
}
