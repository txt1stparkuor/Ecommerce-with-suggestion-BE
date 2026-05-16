package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.constant.enums.SortByDataConstant;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PaginationResponseDto;
import com.txt1stparkuor.Ecommerce.domain.dto.pagination.PagingMeta;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderFilterRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.OrderRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.request.UpdateOrderStatusRequest;
import com.txt1stparkuor.Ecommerce.domain.dto.response.OrderResponse;
import com.txt1stparkuor.Ecommerce.domain.entity.*;
import com.txt1stparkuor.Ecommerce.domain.mapper.OrderMapper;
import com.txt1stparkuor.Ecommerce.exception.InvalidException;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.CartRepository;
import com.txt1stparkuor.Ecommerce.repository.OrderRepository;
import com.txt1stparkuor.Ecommerce.repository.ProductRepository;
import com.txt1stparkuor.Ecommerce.service.OrderService;
import com.txt1stparkuor.Ecommerce.service.specification.OrderSpecification;
import com.txt1stparkuor.Ecommerce.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final OrderSpecification orderSpecification;

    @Override
    @Transactional
    public OrderResponse createOrder(String idempotencyKey, OrderRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Optional<Order> existingOrder = orderRepository.findByIdempotencyKeyAndUserId(idempotencyKey, userId);
        if (existingOrder.isPresent()) {
            return orderMapper.toOrderResponse(existingOrder.get());
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (cart.getCartItems().isEmpty()) {
            throw new InvalidException(ErrorMessage.Cart.ERR_CART_EMPTY);
        }

        Set<String> requestedItemIds = Set.copyOf(request.getCartItemIds());
        List<CartItem> itemsToOrder = cart.getCartItems().stream()
                .filter(item -> requestedItemIds.contains(item.getId()))
                .sorted(Comparator.comparing(item -> item.getProduct().getId()))
                .collect(Collectors.toList());

        if (itemsToOrder.size() != requestedItemIds.size()) {
            throw new InvalidException(ErrorMessage.Cart.ERR_ITEM_NOT_IN_CART);
        }

        double totalAmount = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();

        Order order = Order.builder()
                .user(cart.getUser())
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PENDING)
                .idempotencyKey(idempotencyKey)
                .build();

        for (CartItem cartItem : itemsToOrder) {
            Product product = cartItem.getProduct();
            if (product.getStockQuantity() < cartItem.getQuantity()) {
                throw new InvalidException(ErrorMessage.Product.ERR_NOT_ENOUGH_STOCK,
                        new String[]{product.getName()}, HttpStatus.CONFLICT);
            }
            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(cartItem.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderDetails.add(orderDetail);
            totalAmount += product.getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setOrderDetails(orderDetails);

        try {
            orderRepository.save(order);

        } catch (DataIntegrityViolationException e) {
            Order recoveredOrder = orderRepository.findByIdempotencyKeyAndUserId(idempotencyKey, userId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Order.ERR_RECOVER_IDEMPOTENT));
            return orderMapper.toOrderResponse(recoveredOrder);
        }
        
        cart.getCartItems().removeAll(itemsToOrder);
        cartRepository.save(cart);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toListOrderResponse(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponseDto<OrderResponse> getAllOrders(OrderFilterRequest filterDto) {
        Pageable pageable = PaginationUtil.buildPageable(filterDto, SortByDataConstant.ORDER);
        Specification<Order> spec = orderSpecification.filter(filterDto);

        Page<Order> orderPage = orderRepository.findAll(spec, pageable);

        List<OrderResponse> orderResponses = orderMapper.toListOrderResponse(orderPage.getContent());

        PagingMeta meta = PaginationUtil.buildPagingMeta(filterDto, SortByDataConstant.ORDER, orderPage);

        return new PaginationResponseDto<>(meta, orderResponses);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderStatus(String orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        order.setStatus(request.getStatus());
        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(String orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));

        if (!order.getUser().getId().equals(userId)) {
            throw new InvalidException(ErrorMessage.FORBIDDEN);
        }

        if (order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.PROCESSING) {
            throw new InvalidException(ErrorMessage.Order.ERR_CANCEL_ORDER);
        }

        order.setStatus(OrderStatus.CANCELLED);
        
        // Restore stock
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            product.setStockQuantity(product.getStockQuantity() + detail.getQuantity());
            productRepository.save(product);
        }

        orderRepository.save(order);

        return orderMapper.toOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(String orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = order.getUser().getId().equals(userId);

        if (!isAdmin && !isOwner) {
            throw new InvalidException(ErrorMessage.FORBIDDEN);
        }

        return orderMapper.toOrderResponse(order);
    }
}
