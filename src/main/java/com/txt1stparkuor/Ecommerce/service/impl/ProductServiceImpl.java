package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.constant.SortByDataConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationSortRequestDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductCreationRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ProductUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.ReviewRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.response.ProductResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.ReviewResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Category;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import com.txt1stparkuor.Ecommerce.domain.entity.Review;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import com.txt1stparkuor.Ecommerce.domain.mapper.ProductMapper;
import com.txt1stparkuor.Ecommerce.domain.mapper.ReviewMapper;
import com.txt1stparkuor.Ecommerce.exception.InvalidException;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.CategoryRepository;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.repository.ReviewRepository;
import com.txt1stparkuor.Ecommerce.repository.UserRepository;
import com.txt1stparkuor.Ecommerce.service.ProductService;
import com.txt1stparkuor.Ecommerce.service.specification.ProductSpecification;
import com.txt1stparkuor.Ecommerce.util.PaginationUtil;
import com.txt1stparkuor.Ecommerce.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSpecification productSpecification;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UploadFileUtil uploadFileUtil;

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<ProductResponse> getAllProducts(ProductFilterRequest filterDto) {
        if (filterDto.getMinPrice() != null && filterDto.getMaxPrice() != null && filterDto.getMinPrice() > filterDto.getMaxPrice()) {
            throw new InvalidException(ErrorMessage.Product.ERR_INVALID_PRICE_RANGE);
        }

        Pageable pageable = PaginationUtil.buildPageable(filterDto, SortByDataConstant.PRODUCT);
        Specification<Product> spec = productSpecification.filter(filterDto);

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        List<ProductResponse> productResponses = productMapper.toListProductResponse(productPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(filterDto, SortByDataConstant.PRODUCT, productPage);

        return new PaginationResponseDto<>(meta, productResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<ReviewResponse> getReviewsForProduct(String productId, PaginationSortRequestDto request) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException(ErrorMessage.NOT_FOUND);
        }

        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.REVIEW);
        Page<Review> reviewPage = reviewRepository.findByProductId(productId, pageable);

        List<ReviewResponse> reviewResponses = reviewMapper.toListReviewResponse(reviewPage.getContent());
        PagingMeta meta = PaginationUtil.buildPagingMeta(request, SortByDataConstant.REVIEW, reviewPage);

        return new PaginationResponseDto<>(meta, reviewResponses);
    }

    @Override
    @Transactional
    public ReviewResponse createReview(String productId, ReviewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));

        Review review = Review.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .product(product)
                .user(user)
                .build();

        reviewRepository.save(review);

        int currentCount = product.getRatingCount() != null ? product.getRatingCount() : 0;
        double currentAverage = product.getAverageRating() != null ? product.getAverageRating() : 0.0;

        double newAverage = ((currentAverage * currentCount) + request.getRating()) / (currentCount + 1);
        
        product.setRatingCount(currentCount + 1);
        product.setAverageRating(newAverage);
        productRepository.save(product);

        return reviewMapper.toReviewResponse(review);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductCreationRequest request, MultipartFile imageFile) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        Product product = productMapper.toProduct(request);
        product.setCategory(category);

        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(String id, ProductUpdateRequest request, MultipartFile imageFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        productMapper.updateProductFromRequest(request, product);

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
            product.setCategory(category);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            UploadFileUtil.validateIsImage(imageFile);
            if (product.getImageUrl() != null) {
                uploadFileUtil.destroyImage(product.getImageUrl());
            }
            String imageUrl = uploadFileUtil.uploadImage(imageFile);
            product.setImageUrl(imageUrl);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (product.getImageUrl() != null) {
            uploadFileUtil.destroyImage(product.getImageUrl());
        }

        productRepository.delete(product);
    }
}
