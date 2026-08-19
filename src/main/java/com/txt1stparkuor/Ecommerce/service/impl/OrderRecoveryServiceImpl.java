package com.txt1stparkuor.Ecommerce.service.impl;

import com.txt1stparkuor.Ecommerce.constant.ErrorMessage;
import com.txt1stparkuor.Ecommerce.domain.entity.Order;
import com.txt1stparkuor.Ecommerce.exception.NotFoundException;
import com.txt1stparkuor.Ecommerce.repository.OrderRepository;
import com.txt1stparkuor.Ecommerce.service.OrderRecoveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRecoveryServiceImpl implements OrderRecoveryService {

    private static final int MAX_ATTEMPTS = 3;
    private static final long BASE_DELAY_MS = 50L;

    private final OrderRepository orderRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order recoverByIdempotencyKey(String idempotencyKey, String userId) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            Optional<Order> order = orderRepository.findByIdempotencyKeyAndUserIdWithDetails(idempotencyKey, userId);
            if (order.isPresent()) {
                return order.get();
            }

            if (attempt < MAX_ATTEMPTS) {
                sleep(BASE_DELAY_MS * attempt);
            }
        }

        log.warn("Could not recover order for idempotencyKey={} userId={} after {} attempts",
                idempotencyKey, userId, MAX_ATTEMPTS);
        throw new NotFoundException(ErrorMessage.Order.ERR_RECOVER_IDEMPOTENT);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}