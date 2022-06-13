package com.frizzycode.orderservice.services;

import com.frizzycode.orderservice.models.Order;
import com.frizzycode.orderservice.models.OrderLineItems;
import com.frizzycode.orderservice.models.dto.OrderRequest;
import com.frizzycode.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    @Override
    public void placeOrder(OrderRequest request) {
        orderRepository.save(Order.builder()
                        .orderNumber(UUID.randomUUID().toString())
                        .orderLineItemsList(
                                request.getOrderLineItemsDtoList().stream()
                                .map(orderLineItemsDto -> OrderLineItems.builder()
                                        .price(orderLineItemsDto.getPrice())
                                        .quantity(orderLineItemsDto.getQuantity())
                                        .skuCode(orderLineItemsDto.getSkuCode())
                                        .build()).toList())
                .build());
    }
}
