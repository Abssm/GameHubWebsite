package com.gamehub.service;

import com.gamehub.dto.OrderDto;
import com.gamehub.dto.PaymentRequest;
import com.gamehub.entity.*;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    
    @Transactional
    public OrderDto createOrder(Long userId, PaymentRequest paymentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        List<Cart> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new GamehubException("Cart is empty");
        }
        
        // Calculate totals
        BigDecimal subtotal = calculateSubtotal(cartItems);
        BigDecimal tax = subtotal.multiply(BigDecimal.valueOf(0.15));
        BigDecimal total = subtotal.add(tax);
        
        // Create order
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .user(user)
                .subtotal(subtotal)
                .tax(tax)
                .total(total)
                .currency("USD")
                .status("PROCESSING")
                .orderDate(LocalDateTime.now())
                .build();
        
        Order savedOrder = orderRepository.save(order);
        
        // Create order items and digital codes
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> createOrderItem(savedOrder, cartItem))
                .collect(Collectors.toList());
        
        savedOrder.setItems(orderItems);
        
        // Create payment
        Payment payment = createPayment(savedOrder, paymentRequest);
        savedOrder.setPayment(payment);
        
        // Create digital codes
        List<DigitalCode> digitalCodes = createDigitalCodes(savedOrder, orderItems);
        savedOrder.setDigitalCodes(digitalCodes);
        
        // Update user total spent
        user.setTotalSpent(user.getTotalSpent() + total.doubleValue());
        userRepository.save(user);
        
        // Update game sales count
        updateGameSales(cartItems);
        
        // Create purchase history
        createPurchaseHistory(user, orderItems);
        
        // Clear cart
        cartRepository.deleteByUser(user);
        
        // Update order status
        savedOrder.setStatus("COMPLETED");
        Order finalOrder = orderRepository.save(savedOrder);
        
        return convertToDto(finalOrder);
    }
    
    public List<OrderDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        return orderRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public OrderDto getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GamehubException("Order not found"));
        
        if (!order.getUser().getId().equals(userId)) {
            throw new GamehubException("Unauthorized");
        }
        
        return convertToDto(order);
    }
    
    public List<OrderDto> getRecentOrders() {
        return orderRepository.findRecentOrders().stream()
                .limit(10)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private BigDecimal calculateSubtotal(List<Cart> cartItems) {
        return cartItems.stream()
                .map(item -> item.getGame().getPriceUSD()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    private OrderItem createOrderItem(Order order, Cart cartItem) {
        return OrderItem.builder()
                .order(order)
                .game(cartItem.getGame())
                .quantity(cartItem.getQuantity())
                .unitPrice(cartItem.getGame().getPriceUSD())
                .totalPrice(cartItem.getGame().getPriceUSD()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .gameKey(generateGameKey(cartItem.getGame().getTitle()))
                .build();
    }
    
    private Payment createPayment(Order order, PaymentRequest paymentRequest) {
        return Payment.builder()
                .order(order)
                .paymentMethod("CREDIT_CARD")
                .cardLastFour(paymentRequest.getCardNumber().substring(12))
                .transactionId(UUID.randomUUID().toString())
                .amount(order.getTotal())
                .status("COMPLETED")
                .paymentDate(LocalDateTime.now())
                .build();
    }
    
    private List<DigitalCode> createDigitalCodes(Order order, List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> DigitalCode.builder()
                        .user(order.getUser())
                        .order(order)
                        .gameTitle(item.getGame().getTitle())
                        .code(item.getGameKey())
                        .build())
                .collect(Collectors.toList());
    }
    
    private void updateGameSales(List<Cart> cartItems) {
        cartItems.forEach(cartItem -> {
            Game game = cartItem.getGame();
            game.setSalesCount(game.getSalesCount() + cartItem.getQuantity());
            gameRepository.save(game);
        });
    }
    
    private void createPurchaseHistory(User user, List<OrderItem> orderItems) {
        List<PurchaseHistory> purchaseHistory = orderItems.stream()
                .map(item -> PurchaseHistory.builder()
                        .user(user)
                        .itemName(item.getGame().getTitle())
                        .itemType("GAME")
                        .price(item.getTotalPrice())
                        .purchaseDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        
        purchaseHistoryRepository.saveAll(purchaseHistory);
    }
    
    private String generateOrderNumber() {
        return "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    private String generateGameKey(String gameTitle) {
        String prefix = gameTitle.substring(0, Math.min(4, gameTitle.length()))
                .toUpperCase()
                .replaceAll("\\s", "");
        
        String randomPart = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();
        
        return prefix + "-" + 
               randomPart.substring(0, 4) + "-" +
               randomPart.substring(4, 8) + "-" +
               randomPart.substring(8, 12);
    }
    
    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setSubtotal(order.getSubtotal());
        dto.setTax(order.getTax());
        dto.setTotal(order.getTotal());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        return dto;
    }
}