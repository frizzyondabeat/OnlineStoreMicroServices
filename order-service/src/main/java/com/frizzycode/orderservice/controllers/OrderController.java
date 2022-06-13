package com.frizzycode.orderservice.controllers;

import com.frizzycode.orderservice.models.dto.OrderRequest;
import com.frizzycode.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order/")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> placeOrder(@RequestBody OrderRequest request){
        try {
            log.info("Placing order {}", request);
            orderService.placeOrder(request);
            return new ResponseEntity<>("Order created successfully", HttpStatus.CREATED);
        } catch (Exception exception){
            log.error("Error placing order.\nMessage:{}", exception.getMessage());
            return ResponseEntity.badRequest().body("Unable to place order");
        }
    }
}
