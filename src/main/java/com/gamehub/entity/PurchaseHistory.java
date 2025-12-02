package com.gamehub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "purchase_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Column(nullable = false)
    private String itemName;
    
    @Column(nullable = false)
    private String itemType;
    
    @Column(nullable = false)
    private BigDecimal price;
    
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime purchaseDate = LocalDateTime.now();
}