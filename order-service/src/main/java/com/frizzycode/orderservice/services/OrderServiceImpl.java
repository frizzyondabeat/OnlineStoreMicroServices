package com.frizzycode.orderservice.services;

import com.frizzycode.orderservice.exceptions.OrderInvalidException;
import com.frizzycode.orderservice.models.Order;
import com.frizzycode.orderservice.models.OrderLineItems;
import com.frizzycode.orderservice.models.dto.InventoryResponse;
import com.frizzycode.orderservice.models.dto.OrderRequest;
import com.frizzycode.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    @Override
    public void placeOrder(OrderRequest request) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(
                        request.getOrderLineItemsDtoList().stream()
                                .map(orderLineItemsDto -> OrderLineItems.builder()
                                        .price(orderLineItemsDto.getPrice())
                                        .quantity(orderLineItemsDto.getQuantity())
                                        .skuCode(orderLineItemsDto.getSkuCode())
                                        .build()).toList()
                )
                .build();

        List<InventoryResponse> inventoryResponses = webClient.get().uri("http://localhost:3032/api/v1/inventory/",
                        uriBuilder -> uriBuilder.queryParam(
                                "skuCode",
                                order.getOrderLineItemsList().stream()
                                        .map(OrderLineItems::getSkuCode)
                                        .toList()).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>() {
                })
                .block();

        List<String> skuCode = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        assert inventoryResponses != null;
        boolean isOutOfStock = inventoryResponses.stream()
                .filter(inventoryResponse -> !inventoryResponse.getIsInStock())
                .anyMatch(inventoryResponse -> skuCode.contains(inventoryResponse.getSkuCode()));

        if (!isOutOfStock)
            orderRepository.save(order);
        else
            throw new OrderInvalidException("Product out of stock!!!");

    }
}
