package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationSortRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ReviewRequest;
import com.txt1stparkuor.Ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    @GetMapping(UrlConstant.Product.PRODUCT_COMMON)
    public ResponseEntity<?> getAllProducts(@Valid ProductFilterRequest filterDto) {
        return VsResponseUtil.success(productService.getAllProducts(filterDto));
    }

    @GetMapping(UrlConstant.Product.PRODUCT_ID)
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return VsResponseUtil.success(productService.getProductById(id));
    }

    @GetMapping(UrlConstant.Product.PRODUCT_REVIEWS)
    public ResponseEntity<?> getReviewsForProduct(@PathVariable String id, @Valid PaginationSortRequestDto request) {
        return VsResponseUtil.success(productService.getReviewsForProduct(id, request));
    }

    @PostMapping(UrlConstant.Product.PRODUCT_REVIEWS)
    public ResponseEntity<?> createReview(@PathVariable String id, @Valid @RequestBody ReviewRequest request) {
        return VsResponseUtil.success(productService.createReview(id, request));
    }

    @PostMapping(value = UrlConstant.Product.PRODUCT_COMMON, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @Valid @RequestPart("data") ProductCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(HttpStatus.CREATED, productService.createProduct(request, imageFile));
    }

    @PutMapping(value = UrlConstant.Product.PRODUCT_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @Valid @RequestPart("data") ProductUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(productService.updateProduct(id, request, imageFile));
    }

    @DeleteMapping(UrlConstant.Product.PRODUCT_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return VsResponseUtil.success(HttpStatus.NO_CONTENT, null);
    }
}
