package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UpdateOrderStatusRequest;
import com.txt1stparkuor.Ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    @PostMapping(UrlConstant.Order.ORDER_COMMON)
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest request) {
        return VsResponseUtil.success(orderService.createOrder(request));
    }

    @GetMapping(UrlConstant.Order.GET_MY_ORDERS)
    public ResponseEntity<?> getMyOrders() {
        return VsResponseUtil.success(orderService.getMyOrders());
    }

    @GetMapping(UrlConstant.Order.ORDER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders(@Valid OrderFilterRequest filterDto) {
        return VsResponseUtil.success(orderService.getAllOrders(filterDto));
    }

    @PatchMapping(UrlConstant.Order.UPDATE_ORDER_STATUS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return VsResponseUtil.success(orderService.updateOrderStatus(id, request));
    }

    @PatchMapping(UrlConstant.Order.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable String id) {
        return VsResponseUtil.success(orderService.cancelOrder(id));
    }

    @GetMapping(UrlConstant.Order.ORDER_ID)
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        return VsResponseUtil.success(orderService.getOrderById(id));
    }
}
