package com.gamehub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "games")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String category;
    
    @Column(name = "price_usd", nullable = false)
    private BigDecimal priceUSD;
    
    @Column(name = "price_sar", nullable = false)
    private BigDecimal priceSAR;
    
    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private LocalDate releaseDate;
    
    private String developer;
    
    private String publisher;
    
    private String rating;
    
    @ElementCollection
    private List<String> platforms;
    
    @ElementCollection
    private List<String> tags;
    
    @Column(nullable = false)
    @Builder.Default
    private boolean featured = false;
    
    @Builder.Default
    private Integer stock = 100;
    
    @Builder.Default
    private Integer salesCount = 0;
    
    @OneToMany(mappedBy = "game")
    private List<OrderItem> orderItems;
    
    @OneToMany(mappedBy = "game")
    private List<Wishlist> wishlistItems;
    
    @OneToMany(mappedBy = "game")
    private List<Cart> cartItems;
}