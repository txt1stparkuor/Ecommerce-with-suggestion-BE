package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.CartResponse;

public interface CartService {
    CartResponse addToCart(CartItemRequest request);

    CartResponse getMyCart();

    CartResponse updateCartItem(String cartItemId, CartItemUpdateRequest request);

    CartResponse deleteCartItem(String cartItemId);
}
