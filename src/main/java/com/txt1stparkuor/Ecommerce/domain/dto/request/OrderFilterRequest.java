package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
import com.txt1stparkuor.Ecommerce.domain.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilterRequest extends PaginationFullRequestDto {
    private OrderStatus status;
    private String userId;
}
