package com.gamehub.service;

import com.gamehub.dto.GameDto;
import com.gamehub.dto.UserDto;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.OrderRepository;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final GameService gameService;
    private final ModelMapper modelMapper;
    
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
    
    public UserDto toggleUserBan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        user.setBanned(!user.isBanned());
        User updatedUser = userRepository.save(user);
        
        return modelMapper.map(updatedUser, UserDto.class);
    }
    
    public UserDto makeAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        user.setAdmin(true);
        User updatedUser = userRepository.save(user);
        
        return modelMapper.map(updatedUser, UserDto.class);
    }
    
    public Map<String, Object> getAnalytics() {
        Map<String, Object> analytics = new java.util.HashMap<>();
        
        // Total users
        long totalUsers = userRepository.count();
        analytics.put("totalUsers", totalUsers);
        
        // Total revenue (last 30 days)
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        Double totalRevenue = orderRepository.getTotalRevenue(thirtyDaysAgo).orElse(0.0);
        analytics.put("totalRevenue", totalRevenue);
        
        // Total orders
        long totalOrders = orderRepository.count();
        analytics.put("totalOrders", totalOrders);
        
        // Daily revenue for chart
        List<Object[]> dailyRevenue = orderRepository.getDailyRevenue(thirtyDaysAgo);
        analytics.put("dailyRevenue", dailyRevenue);
        
        // Top selling games
        List<GameDto> topGames = gameService.getTopSellingGames();
        analytics.put("topGames", topGames);
        
        // Recent orders
        analytics.put("recentOrders", orderRepository.findRecentOrders().stream().limit(5).collect(Collectors.toList()));
        
        // User growth
        analytics.put("newUsersThisMonth", 
                userRepository.countByJoinDateAfter(LocalDateTime.now().minusMonths(1)));
        
        return analytics;
    }
    
    public Map<String, Integer> getCategoryStatistics() {
        return gameService.getCategoryCounts();
    }
}