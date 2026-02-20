package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterRequest extends PaginationFullRequestDto {
    private String role;
}
