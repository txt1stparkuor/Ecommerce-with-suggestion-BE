package com.txt1stparkuor.Ecommerce.domain.dto.response;

import com.txt1stparkuor.Ecommerce.domain.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private String id;
    private String orderCode;
    private Instant createdAt;
    private Double totalAmount;
    private OrderStatus status;
    private String shippingAddress;
    private List<OrderDetailResponse> orderDetails;
}
