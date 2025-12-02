package com.gamehub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String orderNumber;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
    
    @Column(nullable = false)
    private BigDecimal subtotal;
    
    @Column(nullable = false)
    private BigDecimal tax;
    
    @Column(nullable = false)
    private BigDecimal total;
    
    @Column(nullable = false)
    private String currency;
    
    @Column(nullable = false)
    @Builder.Default
    private String status = "PENDING";
    
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<DigitalCode> digitalCodes;
}