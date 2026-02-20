package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemUpdateRequest;
import com.txt1stparkuor.Ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    @PostMapping(UrlConstant.Cart.CART_ITEMS)
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartItemRequest request) {
        return VsResponseUtil.success(cartService.addToCart(request));
    }

    @GetMapping(UrlConstant.Cart.CART_COMMON)
    public ResponseEntity<?> getMyCart() {
        return VsResponseUtil.success(cartService.getMyCart());
    }

    @PutMapping(UrlConstant.Cart.CART_ITEM_ID)
    public ResponseEntity<?> updateCartItem(@PathVariable String id, @Valid @RequestBody CartItemUpdateRequest request) {
        return VsResponseUtil.success(cartService.updateCartItem(id, request));
    }

    @DeleteMapping(UrlConstant.Cart.CART_ITEM_ID)
    public ResponseEntity<?> deleteCartItem(@PathVariable String id) {
        return VsResponseUtil.success(cartService.deleteCartItem(id));
    }
}
