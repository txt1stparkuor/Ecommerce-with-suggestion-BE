package com.txt1stparkuor.Ecommerce.domain.dto.pagination;

import com.txt1stparkuor.Ecommerce.constant.CommonConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request DTO for pagination with sorting capabilities")
public class PaginationSortRequestDto extends PaginationRequestDto {
    @Schema(description = "Field name to sort the results by", example = "createdAt")
    private String sortBy = CommonConstant.EMPTY_STRING;

    @Schema(description = "Sort order: true for ascending (ASC), false for descending (DESC)", example = "false")
    private Boolean isAscending = Boolean.FALSE;

}