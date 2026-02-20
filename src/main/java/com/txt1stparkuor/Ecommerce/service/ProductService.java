package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationSortRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ReviewRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.ProductResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.ReviewResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    PaginationResponseDto<ProductResponse> getAllProducts(ProductFilterRequest filterDto);

    ProductResponse getProductById(String id);

    PaginationResponseDto<ReviewResponse> getReviewsForProduct(String productId, PaginationSortRequestDto request);

    ReviewResponse createReview(String productId, ReviewRequest request);

    ProductResponse createProduct(ProductCreationRequest request, MultipartFile imageFile);

    ProductResponse updateProduct(String id, ProductUpdateRequest request, MultipartFile imageFile);

    void deleteProduct(String id);
}
