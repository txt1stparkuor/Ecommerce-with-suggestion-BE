package com.txt1stparkuor.Ecommerce.service;

import com.txt1stparkuor.Ecommerce.domain.entity.Order;

public interface OrderRecoveryService {

    /**
     * Recovers an existing order by idempotency key + userId in a fresh,
     * isolated transaction (REQUIRES_NEW), so it is unaffected by any
     * failed insert/flush in the caller's transaction.
     */
    Order recoverByIdempotencyKey(String idempotencyKey, String userId);
}