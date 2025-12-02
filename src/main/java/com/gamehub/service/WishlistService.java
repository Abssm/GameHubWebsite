package com.gamehub.service;

import com.gamehub.dto.WishlistDto;
import com.gamehub.entity.Game;
import com.gamehub.entity.User;
import com.gamehub.entity.Wishlist;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.GameRepository;
import com.gamehub.repository.UserRepository;
import com.gamehub.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    
    public List<WishlistDto> getUserWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        return wishlistRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public WishlistDto addToWishlist(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GamehubException("Game not found"));
        
        if (wishlistRepository.existsByUserAndGameId(user, gameId)) {
            throw new GamehubException("Game already in wishlist");
        }
        
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .game(game)
                .build();
        
        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return convertToDto(savedWishlist);
    }
    
    public void removeFromWishlist(Long userId, Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new GamehubException("Wishlist item not found"));
        
        if (!wishlist.getUser().getId().equals(userId)) {
            throw new GamehubException("Unauthorized");
        }
        
        wishlistRepository.delete(wishlist);
    }
    
    public boolean isInWishlist(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        return wishlistRepository.existsByUserAndGameId(user, gameId);
    }
    
    private WishlistDto convertToDto(Wishlist wishlist) {
        WishlistDto dto = new WishlistDto();
        dto.setId(wishlist.getId());
        dto.setGameId(wishlist.getGame().getId());
        dto.setGameTitle(wishlist.getGame().getTitle());
        dto.setGameImage(wishlist.getGame().getImageUrl());
        dto.setCategory(wishlist.getGame().getCategory());
        dto.setPrice(wishlist.getGame().getPriceUSD().doubleValue());
        dto.setAddedDate(wishlist.getAddedDate());
        return dto;
    }
}