package com.gamehub.controller;

import com.gamehub.dto.CartItemDto;
import com.gamehub.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;
    
    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCart(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long gameId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(cartService.addToCart(userId, gameId, quantity));
    }
    
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, cartItemId, quantity));
    }
    
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long cartItemId) {
        cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getCartTotal(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(cartService.calculateCartTotal(userId));
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getCartItemCount(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(cartService.getCartItemCount(userId));
    }
}