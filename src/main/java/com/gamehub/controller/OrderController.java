package com.gamehub.controller;

import com.gamehub.dto.OrderDto;
import com.gamehub.dto.PaymentRequest;
import com.gamehub.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.ok(orderService.createOrder(userId, paymentRequest));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(userId, orderId));
    }
}