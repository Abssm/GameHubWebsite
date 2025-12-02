package com.gamehub.service;

import com.gamehub.dto.GameDto;
import com.gamehub.entity.DigitalCode;
import com.gamehub.entity.Order;
import com.gamehub.entity.PurchaseHistory;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.DigitalCodeRepository;
import com.gamehub.repository.OrderRepository;
import com.gamehub.repository.PurchaseHistoryRepository;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final DigitalCodeRepository digitalCodeRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final GameService gameService;
    
    public Map<String, Object> getUserLibrary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        Map<String, Object> library = new HashMap<>();
        
        // Get purchased games
        List<Order> orders = orderRepository.findByUser(user);
        List<GameDto> purchasedGames = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .map(item -> gameService.getGameById(item.getGame().getId()))
                .distinct()
                .collect(Collectors.toList());
        
        // Get digital codes
        List<DigitalCode> digitalCodes = digitalCodeRepository.findByUser(user);
        
        // Get purchase history
        List<PurchaseHistory> purchaseHistory = purchaseHistoryRepository
                .findByUserOrderByPurchaseDateDesc(user);
        
        library.put("purchasedGames", purchasedGames);
        library.put("digitalCodes", digitalCodes);
        library.put("purchaseHistory", purchaseHistory);
        library.put("totalPurchased", purchasedGames.size());
        library.put("totalCodes", digitalCodes.size());
        
        return library;
    }
    
    public List<DigitalCode> getUserDigitalCodes(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        return digitalCodeRepository.findByUser(user);
    }
    
    public List<DigitalCode> getUserUnredeemedCodes(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        return digitalCodeRepository.findByUserAndRedeemedFalse(user);
    }
    
    public DigitalCode redeemCode(Long userId, String code) {
        DigitalCode digitalCode = digitalCodeRepository.findByCode(code)
                .orElseThrow(() -> new GamehubException("Invalid code"));
        
        if (!digitalCode.getUser().getId().equals(userId)) {
            throw new GamehubException("Unauthorized");
        }
        
        if (digitalCode.isRedeemed()) {
            throw new GamehubException("Code already redeemed");
        }
        
        digitalCode.setRedeemed(true);
        digitalCode.setRedeemedAt(java.time.LocalDateTime.now());
        
        return digitalCodeRepository.save(digitalCode);
    }
}