package com.txt1stparkuor.Ecommerce.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private String id;
    private Integer rating;
    private String comment;
    private Instant createdAt;
    private String username;
    private String userFullName;
}
