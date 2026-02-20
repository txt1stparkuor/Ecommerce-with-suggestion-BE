package com.txt1stparkuor.Ecommerce.domain.dto.pagination;

import com.txt1stparkuor.Ecommerce.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationSortRequestDto extends PaginationRequestDto {
    private String sortBy = CommonConstant.EMPTY_STRING;

    private Boolean isAscending = Boolean.FALSE;

}
