package com.gamehub.controller;

import com.gamehub.dto.WishlistDto;
import com.gamehub.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    
    private final WishlistService wishlistService;
    
    @GetMapping
    public ResponseEntity<List<WishlistDto>> getWishlist(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(wishlistService.getUserWishlist(userId));
    }
    
    @PostMapping("/add/{gameId}")
    public ResponseEntity<WishlistDto> addToWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long gameId) {
        return ResponseEntity.ok(wishlistService.addToWishlist(userId, gameId));
    }
    
    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> removeFromWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long wishlistId) {
        wishlistService.removeFromWishlist(userId, wishlistId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/check/{gameId}")
    public ResponseEntity<Boolean> checkInWishlist(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long gameId) {
        return ResponseEntity.ok(wishlistService.isInWishlist(userId, gameId));
    }
}