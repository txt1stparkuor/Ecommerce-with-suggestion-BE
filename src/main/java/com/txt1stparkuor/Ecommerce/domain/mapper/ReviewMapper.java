package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.response.ReviewResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.fullName", target = "userFullName")
    ReviewResponse toReviewResponse(Review review);

    List<ReviewResponse> toListReviewResponse(List<Review> reviews);
}
