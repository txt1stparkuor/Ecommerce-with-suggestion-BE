package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.PythonRecommendationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RecommendedProductDto;
import com.txt1stparkuor.Ecommerce.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final RestTemplate restTemplate;

    @Value("${python.service.url:http://localhost:8000}")
    private String pythonServiceUrl;

    @Override
    public PaginationResponseDto<RecommendedProductDto> getSimilarProducts(String productId, PaginationRequestDto request) {
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + "/api/recommendations/similar/" + productId)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .toUriString();

        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            if (response != null && response.getData() != null) {
                return new PaginationResponseDto<>(response.getPagingMeta(), response.getData());
            }
        } catch (Exception e) {
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
        return new PaginationResponseDto<>(null, Collections.emptyList());
    }

    @Override
    public PaginationResponseDto<RecommendedProductDto> getUserRecommendations(String userId, PaginationRequestDto request) {
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + "/api/recommendations/user/" + userId)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .toUriString();

        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            if (response != null && response.getData() != null) {
                return new PaginationResponseDto<>(response.getPagingMeta(), response.getData());
            }
        } catch (Exception e) {
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
        return new PaginationResponseDto<>(null, Collections.emptyList());
    }

    @Override
    public PaginationResponseDto<RecommendedProductDto> getHybridProductsRecommendations(String productId, String userId, PaginationRequestDto request) {
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + "/api/recommendations/hybrid/" + userId)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .queryParam("product_id", productId)
                .toUriString();
        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            if (response != null && response.getData() != null) {
                return new PaginationResponseDto<>(response.getPagingMeta(), response.getData());
            }
        } catch (Exception e) {
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
        return new PaginationResponseDto<>(null, Collections.emptyList());
    }
}
