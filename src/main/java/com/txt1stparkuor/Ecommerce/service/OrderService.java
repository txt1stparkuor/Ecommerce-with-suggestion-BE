package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UpdateOrderStatusRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getMyOrders();

    PaginationResponseDto<OrderResponse> getAllOrders(OrderFilterRequest filterDto);

    OrderResponse updateOrderStatus(String orderId, UpdateOrderStatusRequest request);

    OrderResponse cancelOrder(String orderId);

    OrderResponse getOrderById(String orderId);
}
