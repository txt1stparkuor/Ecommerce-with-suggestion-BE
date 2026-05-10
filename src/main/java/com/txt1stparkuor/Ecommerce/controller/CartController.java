package com.txt1stparkuor.Ecommerce.controller;

import com.txt1stparkuor.Ecommerce.annotation.ApiCommonResponses;
import com.txt1stparkuor.Ecommerce.base.RestApiV1;
import com.txt1stparkuor.Ecommerce.base.VsResponseUtil;
import com.txt1stparkuor.Ecommerce.constant.UrlConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemUpdateRequest;
import com.txt1stparkuor.Ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Cart Management", description = "Operations related to user shopping carts and cart items")
@ApiCommonResponses
public class CartController {

    CartService cartService;

    @Operation(
            summary = "Add item to cart",
            description = "Adds a specified product with a given quantity to the authenticated user's cart. If the item already exists, its quantity will be updated."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added to cart successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping(UrlConstant.Cart.CART_ITEMS)
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartItemRequest request) {
        return VsResponseUtil.success(cartService.addToCart(request));
    }

    @Operation(
            summary = "Get user's cart",
            description = "Retrieves the current authenticated user's shopping cart details, including all items and their quantities."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found for the user (a new cart might be created implicitly)")
    })
    @GetMapping(UrlConstant.Cart.CART_COMMON)
    public ResponseEntity<?> getMyCart() {
        return VsResponseUtil.success(cartService.getMyCart());
    }

    @Operation(
            summary = "Update item in cart",
            description = "Updates the quantity of a specific item in the authenticated user's cart. The item is identified by its cart item ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item quantity updated successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found with the given ID")
    })
    @PutMapping(UrlConstant.Cart.CART_ITEM_ID)
    public ResponseEntity<?> updateCartItem(@PathVariable String id, @Valid @RequestBody CartItemUpdateRequest request) {
        return VsResponseUtil.success(cartService.updateCartItem(id, request));
    }

    @Operation(
            summary = "Delete item from cart",
            description = "Removes a specific item from the authenticated user's cart based on its cart item ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found with the given ID")
    })
    @DeleteMapping(UrlConstant.Cart.CART_ITEM_ID)
    public ResponseEntity<?> deleteCartItem(@PathVariable String id) {
        return VsResponseUtil.success(cartService.deleteCartItem(id));
    }
}