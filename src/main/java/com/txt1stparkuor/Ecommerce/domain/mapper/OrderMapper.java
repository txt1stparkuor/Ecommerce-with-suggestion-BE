package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.response.OrderDetailResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.OrderResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Order;
import com.txt1stparkuor.Ecommerce.domain.entity.OrderDetail;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.imageUrl", target = "productImageUrl")
    @Mapping(target = "totalPrice", expression = "java(orderDetail.getPrice() * orderDetail.getQuantity())")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    List<OrderDetailResponse> toListOrderDetailResponse(List<OrderDetail> orderDetails);

    @Mapping(source = "orderDetails", target = "orderDetails")
    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toListOrderResponse(List<Order> orders);
}
