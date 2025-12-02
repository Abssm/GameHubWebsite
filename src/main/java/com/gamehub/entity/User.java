package com.gamehub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String fullName;
    
    @Builder.Default
    @Column(name = "join_date")
    private LocalDateTime joinDate = LocalDateTime.now();
    
    @Builder.Default
    private boolean isAdmin = false;
    
    @Builder.Default
    private boolean isBanned = false;
    
    @Builder.Default
    private Double totalSpent = 0.0;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Wishlist> wishlistItems;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> cartItems;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PurchaseHistory> purchaseHistory;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = isAdmin ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(role));
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return !isBanned;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Column(name = "preferred_language")
    @Builder.Default
    private String preferredLanguage = "en";

    @Column(name = "preferred_currency")
    @Builder.Default
    private String preferredCurrency = "USD";

    @ElementCollection
    @CollectionTable(name = "user_notification_preferences", 
                     joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "notification_type")
    @Column(name = "enabled")
    @Builder.Default
    private Map<String, Boolean> notificationPreferences = new HashMap<>();

    @Column(name = "theme")
    @Builder.Default
    private String theme = "dark";

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "reset_token_expiry")
    private LocalDateTime resetTokenExpiry;

    @Column(name = "deleted")
    @Builder.Default
    private boolean deleted = false;

    @Column(name = "deleted_at")
private LocalDateTime deletedAt;
}