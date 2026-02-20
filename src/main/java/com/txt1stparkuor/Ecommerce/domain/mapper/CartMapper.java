package com.txt1stparkuor.Ecommerce.domain.mapper;

import com.txt1stparkuor.Ecommerce.domain.dto.response.CartItemResponse;
import com.txt1stparkuor.Ecommerce.domain.dto.response.CartResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Cart;
import com.txt1stparkuor.Ecommerce.domain.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.imageUrl", target = "productImageUrl")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(target = "totalPrice", expression = "java(cartItem.getProduct().getPrice() * cartItem.getQuantity())")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    List<CartItemResponse> toListCartItemResponse(List<CartItem> cartItems);

    @Mapping(source = "cartItems", target = "items")
    @Mapping(target = "totalPrice", expression = "java(calculateCartTotal(cart))")
    CartResponse toCartResponse(Cart cart);

    default Double calculateCartTotal(Cart cart) {
        if (cart.getCartItems() == null) {
            return 0.0;
        }
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
