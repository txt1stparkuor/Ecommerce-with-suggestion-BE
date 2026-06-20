package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RecommendedProductDto;

public interface RecommendationService {
    PaginationResponseDto<RecommendedProductDto> getSimilarProducts(String productId, PaginationRequestDto request);

    PaginationResponseDto<RecommendedProductDto> getUserRecommendations(PaginationRequestDto request);

    PaginationResponseDto<RecommendedProductDto> getHybridProductsRecommendations(String productId, PaginationRequestDto request);
}
