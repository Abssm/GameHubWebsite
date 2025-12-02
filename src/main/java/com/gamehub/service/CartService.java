package com.gamehub.service;

import com.gamehub.dto.CartItemDto;
import com.gamehub.entity.Cart;
import com.gamehub.entity.Game;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.CartRepository;
import com.gamehub.repository.GameRepository;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    
    private final CartRepository cartRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    
    public List<CartItemDto> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        return cartRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public CartItemDto addToCart(Long userId, Long gameId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GamehubException("Game not found"));
        
        Cart existingCartItem = cartRepository.findByUserAndGameId(user, gameId).orElse(null);
        
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartRepository.save(existingCartItem);
            return convertToDto(existingCartItem);
        }
        
        Cart cartItem = Cart.builder()
                .user(user)
                .game(game)
                .quantity(quantity)
                .build();
        
        Cart savedCartItem = cartRepository.save(cartItem);
        return convertToDto(savedCartItem);
    }
    
    public CartItemDto updateCartItem(Long userId, Long cartItemId, Integer quantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new GamehubException("Cart item not found"));
        
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new GamehubException("Unauthorized");
        }
        
        cartItem.setQuantity(quantity);
        Cart updatedCartItem = cartRepository.save(cartItem);
        return convertToDto(updatedCartItem);
    }
    
    public void removeFromCart(Long userId, Long cartItemId) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new GamehubException("Cart item not found"));
        
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new GamehubException("Unauthorized");
        }
        
        cartRepository.delete(cartItem);
    }
    
    @Transactional
    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        cartRepository.deleteByUser(user);
    }
    
    public BigDecimal calculateCartTotal(Long userId) {
        List<CartItemDto> cartItems = getCartItems(userId);
        return cartItems.stream()
                .map(item -> BigDecimal.valueOf(item.getTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public Integer getCartItemCount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        return cartRepository.countByUser(user);
    }
    
    private CartItemDto convertToDto(Cart cart) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cart.getId());
        dto.setGameId(cart.getGame().getId());
        dto.setGameTitle(cart.getGame().getTitle());
        dto.setGameImage(cart.getGame().getImageUrl());
        dto.setCategory(cart.getGame().getCategory());
        dto.setPrice(cart.getGame().getPriceUSD().doubleValue());
        dto.setQuantity(cart.getQuantity());
        dto.setTotal(cart.getGame().getPriceUSD()
                .multiply(BigDecimal.valueOf(cart.getQuantity()))
                .doubleValue());
        return dto;
    }
}