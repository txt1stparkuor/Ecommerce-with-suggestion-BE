package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.PythonRecommendationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.RecommendedProductDto;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import com.txt1stparkuor.Ecommerce.domain.mapper.ProductMapper;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final RestTemplate restTemplate;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Value("${python.service.url:http://localhost:8000}")
    private String pythonServiceUrl;

    @Override
    public PaginationResponseDto<RecommendedProductDto> getSimilarProducts(String productId, PaginationRequestDto request) {
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + UrlConstant.Recommendation.SIMILAR_PRODUCTS)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .buildAndExpand(productId)
                .toUriString();
        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            return processPythonResponse(response);
        } catch (Exception e) {
            log.error("Failed to fetch similar products from Python service: {}", e.getMessage(), e);
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
    }

    @Override
    public PaginationResponseDto<RecommendedProductDto> getUserRecommendations(PaginationRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + UrlConstant.Recommendation.PRODUCTS_FOR_USER)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .buildAndExpand(userId)
                .toUriString();

        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            return processPythonResponse(response);
        } catch (Exception e) {
            log.error("Failed to fetch user recommendations from Python service: {}", e.getMessage(), e);
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
    }

    @Override
    public PaginationResponseDto<RecommendedProductDto> getHybridProductsRecommendations(String productId, PaginationRequestDto request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        String url = UriComponentsBuilder.fromUriString(pythonServiceUrl + UrlConstant.Recommendation.HYBRID)
                .queryParam("limit", request.getPageSize())
                .queryParam("page", request.getPageNum() + 1)
                .queryParam("product_id", productId)
                .buildAndExpand(userId)
                .toUriString();
        try {
            PythonRecommendationResponseDto response = restTemplate.getForObject(url, PythonRecommendationResponseDto.class);
            return processPythonResponse(response);

        } catch (Exception e) {
            log.error("Failed to fetch hybrid products recommendations from Python service: {}", e.getMessage(), e);
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }
    }

    private PaginationResponseDto<RecommendedProductDto> processPythonResponse(PythonRecommendationResponseDto response) {
        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            return new PaginationResponseDto<>(null, Collections.emptyList());
        }

        List<RecommendedProductDto> pythonItems = response.getData();
        List<String> productIds = pythonItems.stream()
                .map(RecommendedProductDto::getProductId)
                .collect(Collectors.toList());
        Map<String, Product> productDbMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<RecommendedProductDto> finalItems = new ArrayList<>();
        for (RecommendedProductDto item : pythonItems) {
            Product dbProduct = productDbMap.get(item.getProductId());
            if (dbProduct != null) {
                productMapper.updateRecommendedProductFromDb(dbProduct, item);
                finalItems.add(item);
            }
        }

        return new PaginationResponseDto<>(response.getPagingMeta(), finalItems);
    }
}