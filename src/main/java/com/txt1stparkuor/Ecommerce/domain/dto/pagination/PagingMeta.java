package com.txt1stparkuor.Ecommerce.domain.dto.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Metadata for pagination details")
public class PagingMeta {
    @Schema(description = "Total number of elements across all pages", example = "100")
    private Long totalElements;
    @Schema(description = "Total number of pages available", example = "10")
    private Integer totalPages;
    @Schema(description = "Current page number", example = "1")
    private Integer pageNum;
    @Schema(description = "Number of elements per page", example = "10")
    private Integer pageSize;
    @Schema(description = "The field being sorted", example = "createdAt")
    private String sortBy;
    @Schema(description = "Sort order (ASC or DESC)", example = "DESC")
    private String sortType;
}
