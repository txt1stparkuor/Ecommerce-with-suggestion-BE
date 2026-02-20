package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.CartItemUpdateRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.CartResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.Cart;
import com.txt1stparkuor.Ecommerce.domain.entity.CartItem;
import com.txt1stparkuor.Ecommerce.domain.entity.Product;
import com.txt1stparkuor.Ecommerce.domain.entity.User;
import com.txt1stparkuor.Ecommerce.domain.mapper.CartMapper;
import com.txt1stparkuor.Ecommerce.exception.InvalidException;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.CartItemRepository;
import com.txt1stparkuor.Ecommerce.repository.CartRepository;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.repository.UserRepository;
import com.txt1stparkuor.Ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponse addToCart(CartItemRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_NOT_ENOUGH_STOCK);
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID));
                    Cart newCart = Cart.builder()
                            .user(user)
                            .cartItems(new ArrayList<>())
                            .build();
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Cart cart = cartRepository.findByUserId(userId)
                .orElse(Cart.builder().cartItems(new ArrayList<>()).build());

        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(String cartItemId, CartItemUpdateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new InvalidException(ErrorMessage.FORBIDDEN);
        }

        if (cartItem.getProduct().getStockQuantity() < request.getQuantity()) {
            throw new InvalidException(ErrorMessage.Product.ERR_NOT_ENOUGH_STOCK);
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return cartMapper.toCartResponse(cartItem.getCart());
    }

    @Override
    @Transactional
    public CartResponse deleteCartItem(String cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new InvalidException(ErrorMessage.FORBIDDEN);
        }

        Cart cart = cartItem.getCart();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return cartMapper.toCartResponse(cart);
    }
}
