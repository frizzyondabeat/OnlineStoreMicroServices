package com.frizzycode.orderservice.services;

import com.frizzycode.orderservice.models.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest request);
}
