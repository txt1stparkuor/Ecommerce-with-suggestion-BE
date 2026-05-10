package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UpdateOrderStatusRequest;
import com.txt1stparkuor.Ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Order Management", description = "Operations related to customer orders")
@ApiCommonResponses
public class OrderController {

    OrderService orderService;

    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for the authenticated user based on the items in their cart."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found for the user"),
            @ApiResponse(responseCode = "400", description = "Cart is empty or requested item not in cart or not enough stock for a product")
    })
    @PostMapping(UrlConstant.Order.ORDER_COMMON)
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest request) {
        return VsResponseUtil.success(orderService.createOrder(request));
    }

    @Operation(
            summary = "Get current user's orders",
            description = "Retrieves a list of all orders placed by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's orders")
    })
    @GetMapping(UrlConstant.Order.GET_MY_ORDERS)
    public ResponseEntity<?> getMyOrders() {
        return VsResponseUtil.success(orderService.getMyOrders());
    }

    @Operation(
            summary = "Get all orders (Admin only)",
            description = "Retrieves a filtered list of all orders in the system. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders")
    })
    @GetMapping(UrlConstant.Order.ORDER_COMMON)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders(@Valid OrderFilterRequest filterDto) {
        return VsResponseUtil.success(orderService.getAllOrders(filterDto));
    }

    @Operation(
            summary = "Update order status (Admin only)",
            description = "Updates the status of a specific order. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PatchMapping(UrlConstant.Order.UPDATE_ORDER_STATUS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(@PathVariable String id, @Valid @RequestBody UpdateOrderStatusRequest request) {
        return VsResponseUtil.success(orderService.updateOrderStatus(id, request));
    }

    @Operation(
            summary = "Cancel an order",
            description = "Cancels a specific order placed by the authenticated user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "400", description = "Order cannot be cancelled (status not PENDING or PROCESSING)"),
    })
    @PatchMapping(UrlConstant.Order.CANCEL_ORDER)
    public ResponseEntity<?> cancelOrder(@PathVariable String id) {
        return VsResponseUtil.success(orderService.cancelOrder(id));
    }

    @Operation(
            summary = "Get order by ID",
            description = "Retrieves details of a specific order by its ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
    })
    @GetMapping(UrlConstant.Order.ORDER_ID)
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        return VsResponseUtil.success(orderService.getOrderById(id));
    }
}