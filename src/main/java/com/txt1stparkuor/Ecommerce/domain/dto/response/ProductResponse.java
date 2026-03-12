package com.txt1stparkuor.Ecommerce.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private Double price;
    private Double originalPrice;
    private String imageUrl;
    private Integer stockQuantity;
    private Double averageRating;
    private Long ratingCount;
    private String categoryId;
    private String categoryPath;
    private Double discountPercentage;
}
