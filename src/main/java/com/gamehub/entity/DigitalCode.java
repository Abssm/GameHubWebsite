package com.gamehub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "digital_codes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DigitalCode {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Column(nullable = false)
    private String gameTitle;
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean redeemed = false;
    
    @Builder.Default
    private LocalDateTime generatedAt = LocalDateTime.now();
    
    private LocalDateTime redeemedAt;
}