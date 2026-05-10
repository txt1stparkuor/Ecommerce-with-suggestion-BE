package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationSortRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ReviewRequest;
import com.txt1stparkuor.Ecommerce.service.ProductService;
import com.txt1stparkuor.Ecommerce.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Management", description = "Operations related to product catalog and reviews")
public class ProductController {

    ProductService productService;
    RecommendationService recommendationService;

    @Operation(
            summary = "Get all products",
            description = "Retrieves a paginated and filterable list of all products."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved products"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid filter or pagination parameters"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Product.PRODUCT_COMMON)
    public ResponseEntity<?> getAllProducts(@Valid ProductFilterRequest filterDto) {
        return VsResponseUtil.success(productService.getAllProducts(filterDto));
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a single product's details by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product ID format"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Product.PRODUCT_ID)
    public ResponseEntity<?> getProductById(@PathVariable String id) {
        return VsResponseUtil.success(productService.getProductById(id));
    }

    @Operation(
            summary = "Get reviews for a product",
            description = "Retrieves a paginated list of reviews for a specific product."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reviews"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product ID format or pagination parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Product.PRODUCT_REVIEWS)
    public ResponseEntity<?> getReviewsForProduct(@PathVariable String id, @Valid PaginationSortRequestDto request) {
        return VsResponseUtil.success(productService.getReviewsForProduct(id, request));
    }

    @Operation(
            summary = "Create a review for a product",
            description = "Submits a new review for a specific product by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid review data or product ID format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(UrlConstant.Product.PRODUCT_REVIEWS)
    public ResponseEntity<?> createReview(@PathVariable String id, @Valid @RequestBody ReviewRequest request) {
        return VsResponseUtil.success(productService.createReview(id, request));
    }

    @Operation(
            summary = "Create a new product (Admin only)",
            description = "Creates a new product with details and an optional image. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product data or category ID format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Product name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping(value = UrlConstant.Product.PRODUCT_COMMON, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(
            @Valid @RequestPart("data") ProductCreationRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(HttpStatus.CREATED, productService.createProduct(request, imageFile));
    }

    @Operation(
            summary = "Update an existing product (Admin only)",
            description = "Updates an existing product's details and optional image by its ID. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product data or category ID format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)"),
            @ApiResponse(responseCode = "404", description = "Product or Category not found"),
            @ApiResponse(responseCode = "409", description = "Conflict: Product name already exists"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping(value = UrlConstant.Product.PRODUCT_ID, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @Valid @RequestPart("data") ProductUpdateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        return VsResponseUtil.success(productService.updateProduct(id, request, imageFile));
    }

    @Operation(
            summary = "Delete a product (Admin only)",
            description = "Deletes a product by its unique ID. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product ID format"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges (ADMIN role required)"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping(UrlConstant.Product.PRODUCT_ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return VsResponseUtil.success(HttpStatus.NO_CONTENT, null);
    }

    @Operation(
            summary = "Get similar products",
            description = "Retrieves a list of products similar to the one specified by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved similar products"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product ID format or pagination parameters"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @SecurityRequirements()
    @GetMapping(UrlConstant.Product.PRODUCT_RECOMMENDATIONS)
    public ResponseEntity<?> getSimilarProducts(@PathVariable String id, @Valid PaginationRequestDto request) {
        return VsResponseUtil.success(recommendationService.getSimilarProducts(id, request));
    }

    @Operation(
            summary = "Get hybrid product recommendations",
            description = "Retrieves hybrid product recommendations for a given product and authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved hybrid recommendations"),
            @ApiResponse(responseCode = "400", description = "Bad Request: Invalid product ID format or pagination parameters"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping(UrlConstant.Product.PRODUCT_RECOMMENDATIONS_HYBRID)
    public ResponseEntity<?> getHybridProductsRecommendations(@PathVariable String id, @Valid PaginationRequestDto request) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return VsResponseUtil.success(recommendationService.getHybridProductsRecommendations(id, userId, request));
    }
}