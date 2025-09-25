package com.app.ecom_application.controller;

import com.app.ecom_application.dto.OrderResponseDto;
import com.app.ecom_application.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Optional<OrderResponseDto>> createOrder(@RequestHeader("X-User-Id") Long userId ){
        return new ResponseEntity<>(orderService.createOrder(userId), HttpStatus.CREATED);
    }
}
