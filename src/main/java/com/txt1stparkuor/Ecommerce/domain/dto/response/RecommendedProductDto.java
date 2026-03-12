package com.txt1stparkuor.Ecommerce.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedProductDto {
    private String productId;
    private String productName;
    private String category;
    private Double discountedPrice;
    private Double actualPrice;
    private Double rating;
    private Long ratingCount;
    private String imgLink;
    private Double similarityScore;
    private Double predictedRating;
    private String type;
}
