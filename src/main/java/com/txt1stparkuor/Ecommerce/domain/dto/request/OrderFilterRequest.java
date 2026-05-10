package com.txt1stparkuor.Ecommerce.domain.dto.request;

import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationFullRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request DTO for filtering orders with pagination")
public class OrderFilterRequest extends PaginationFullRequestDto {
    @Schema(description = "Status of the order to filter by", example = "PENDING")
    private OrderStatus status;
    @Schema(description = "ID of the user to filter orders for", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    private String userId;
}
