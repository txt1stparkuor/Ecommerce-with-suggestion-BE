package com.txt1stparkuor.Ecommerce.domain.dto.response;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PythonRecommendationResponseDto {
    private List<RecommendedProductDto> data;
    private PagingMeta pagingMeta;
}
