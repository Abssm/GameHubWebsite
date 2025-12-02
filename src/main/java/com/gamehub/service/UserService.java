package com.gamehub.service;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserUpdateRequest;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.OrderRepository;
import com.gamehub.repository.UserRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    /**
     * Get user profile by ID
     */
    public UserDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        return enrichUserDto(user);
    }

    /**
     * Update user profile
     */
    @Transactional
    public UserDto updateUserProfile(Long userId, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));

        // Update basic information
        if (updateRequest.getFullName() != null && !updateRequest.getFullName().isEmpty()) {
            user.setFullName(updateRequest.getFullName());
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            if (!user.getEmail().equals(updateRequest.getEmail())) {
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    throw new GamehubException("Email already in use");
                }
                user.setEmail(updateRequest.getEmail());
            }
        }

        // Update password if provided
        if (updateRequest.getCurrentPassword() != null && 
            updateRequest.getNewPassword() != null) {
            
            if (!passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
                throw new GamehubException("Current password is incorrect");
            }
            
            if (updateRequest.getNewPassword().length() < 6) {
                throw new GamehubException("New password must be at least 6 characters");
            }
            
            user.setPassword(passwordEncoder.encode(updateRequest.getNewPassword()));
            
            // Send password change notification
            sendPasswordChangeNotification(user);
        }

        User updatedUser = userRepository.save(user);
        return enrichUserDto(updatedUser);
    }

    /**
     * Get all users (for admin)
     */
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::enrichUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Search users by email or name (for admin)
     */
    public List<UserDto> searchUsers(String query) {
        return userRepository.findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(query, query).stream()
                .map(this::enrichUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Ban/Unban user (admin only)
     */
    @Transactional
    public UserDto toggleUserBan(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        user.setBanned(!user.isBanned());
        User updatedUser = userRepository.save(user);
        
        // Send ban/unban notification
        sendBanNotification(updatedUser);
        
        return enrichUserDto(updatedUser);
    }

    /**
     * Make user admin (admin only)
     */
    @Transactional
    public UserDto makeUserAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        user.setAdmin(true);
        User updatedUser = userRepository.save(user);
        
        // Send admin promotion notification
        sendAdminPromotionNotification(updatedUser);
        
        return enrichUserDto(updatedUser);
    }

    /**
     * Delete user account
     */
    @Transactional
    public void deleteUserAccount(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        // Verify password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new GamehubException("Incorrect password");
        }
        
        // Send account deletion notification
        sendAccountDeletionNotification(user);
        
        // Soft delete (mark as deleted)
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * Get user statistics for dashboard
     */
    public UserStatistics getUserStatistics(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        // Total spent
        Double totalSpent = user.getTotalSpent() != null ? user.getTotalSpent() : 0.0;
        
        // Total orders
        int orderCount = orderRepository.countByUser(user);
        
        // Average order value
        Double averageOrderValue = orderCount > 0 ? totalSpent / orderCount : 0.0;
        
        // Days since join
        long daysSinceJoin = java.time.temporal.ChronoUnit.DAYS.between(
                user.getJoinDate().toLocalDate(), 
                java.time.LocalDate.now()
        );
        
        // Last order date
        LocalDateTime lastOrderDate = orderRepository.findFirstByUserOrderByOrderDateDesc(user)
                .map(order -> order.getOrderDate())
                .orElse(null);
        
        return UserStatistics.builder()
                .totalSpent(totalSpent)
                .totalOrders(orderCount)
                .averageOrderValue(averageOrderValue)
                .daysSinceJoin(daysSinceJoin)
                .lastOrderDate(lastOrderDate)
                .preferences(user.getNotificationPreferences())
                .build();
    }

    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Get user by email
     */
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GamehubException("User not found"));
        return enrichUserDto(user);
    }

    /**
     * Get users by join date range (for admin analytics)
     */
    public List<UserDto> getUsersByJoinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return userRepository.findByJoinDateBetween(startDate, endDate).stream()
                .map(this::enrichUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Get top spenders (for admin)
     */
    public List<UserDto> getTopSpenders(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return userRepository.findTopSpenders(pageable).stream()
                .map(this::enrichUserDto)
                .collect(Collectors.toList());
    }

    /**
     * Update user preferences
     */
    @Transactional
    public UserDto updateUserPreferences(Long userId, Map<String, Object> preferences) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        // Update preferences
        if (preferences.containsKey("language")) {
            user.setPreferredLanguage((String) preferences.get("language"));
        }
        
        if (preferences.containsKey("currency")) {
            user.setPreferredCurrency((String) preferences.get("currency"));
        }
        
        if (preferences.containsKey("notifications")) {
            @SuppressWarnings("unchecked")
            Map<String, Boolean> notificationPrefs = (Map<String, Boolean>) preferences.get("notifications");
            user.setNotificationPreferences(notificationPrefs);
        }
        
        if (preferences.containsKey("theme")) {
            user.setTheme((String) preferences.get("theme"));
        }
        
        User updatedUser = userRepository.save(user);
        return enrichUserDto(updatedUser);
    }

    /**
     * Request password reset
     */
    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        // Generate reset token
        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);
        
        // Send reset email
        sendPasswordResetEmail(user, resetToken);
    }

    /**
     * Reset password with token
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new GamehubException("Invalid or expired reset token"));
        
        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new GamehubException("Reset token has expired");
        }
        
        if (newPassword.length() < 6) {
            throw new GamehubException("Password must be at least 6 characters");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        
        // Send password changed notification
        sendPasswordChangedNotification(user);
    }

    /**
     * Enrich UserDto with additional information
     */
    private UserDto enrichUserDto(User user) {
        UserDto dto = modelMapper.map(user, UserDto.class);
        
        // Calculate additional fields
        int orderCount = orderRepository.countByUser(user);
        dto.setOrderCount(orderCount);
        
        return dto;
    }

    /**
     * Send password change notification
     */
    private void sendPasswordChangeNotification(User user) {
        try {
            String subject = "Password Changed Successfully";
            String body = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: #ff0000; color: white; padding: 20px; text-align: center; }
                        .content { background: #f9f9f9; padding: 30px; }
                        .footer { text-align: center; color: #666; font-size: 12px; margin-top: 30px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>GameHub Security Alert</h1>
                        </div>
                        <div class="content">
                            <h2>Hello %s,</h2>
                            <p>Your password was successfully changed.</p>
                            <p><strong>Date:</strong> %s</p>
                            <p>If you did not make this change, please contact our support team immediately.</p>
                        </div>
                        <div class="footer">
                            <p>© 2024 GameHub. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(user.getFullName(), LocalDateTime.now().toString());
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            // Log but don't throw
            System.err.println("Failed to send password change notification: " + e.getMessage());
        }
    }

    /**
     * Send ban notification
     */
    private void sendBanNotification(User user) {
        try {
            String action = user.isBanned() ? "banned" : "unbanned";
            String subject = "Account " + action + " - GameHub";
            String body = """
                <!DOCTYPE html>
                <html>
                <body>
                    <h2>Account Status Update</h2>
                    <p>Hello %s,</p>
                    <p>Your account has been <strong>%s</strong> from GameHub.</p>
                    <p>Date: %s</p>
                    <p>If you believe this is a mistake, please contact support.</p>
                </body>
                </html>
                """.formatted(user.getFullName(), action, LocalDateTime.now().toString());
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            System.err.println("Failed to send ban notification: " + e.getMessage());
        }
    }

    /**
     * Send admin promotion notification
     */
    private void sendAdminPromotionNotification(User user) {
        try {
            String subject = "Welcome to GameHub Admin Team";
            String body = """
                <!DOCTYPE html>
                <html>
                <body>
                    <h2>Congratulations!</h2>
                    <p>Hello %s,</p>
                    <p>You have been promoted to Administrator role on GameHub.</p>
                    <p>You now have access to the admin panel and additional privileges.</p>
                    <p>Date: %s</p>
                </body>
                </html>
                """.formatted(user.getFullName(), LocalDateTime.now().toString());
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            System.err.println("Failed to send admin promotion notification: " + e.getMessage());
        }
    }

    /**
     * Send account deletion notification
     */
    private void sendAccountDeletionNotification(User user) {
        try {
            String subject = "Account Deletion - GameHub";
            String body = """
                <!DOCTYPE html>
                <html>
                <body>
                    <h2>Account Deletion Confirmation</h2>
                    <p>Hello %s,</p>
                    <p>Your GameHub account has been successfully deleted.</p>
                    <p>We're sorry to see you go. If you change your mind, you can create a new account anytime.</p>
                    <p>Date: %s</p>
                </body>
                </html>
                """.formatted(user.getFullName(), LocalDateTime.now().toString());
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            System.err.println("Failed to send account deletion notification: " + e.getMessage());
        }
    }

    /**
     * Send password reset email
     */
    private void sendPasswordResetEmail(User user, String token) {
        try {
            String subject = "Password Reset - GameHub";
            String resetLink = "http://localhost:5500/#reset-password?token=" + token;
            String body = """
                <!DOCTYPE html>
                <html>
                <body>
                    <h2>Password Reset Request</h2>
                    <p>Hello %s,</p>
                    <p>You requested a password reset. Click the link below to reset your password:</p>
                    <p><a href="%s">Reset Password</a></p>
                    <p>This link will expire in 24 hours.</p>
                    <p>If you didn't request this, please ignore this email.</p>
                </body>
                </html>
                """.formatted(user.getFullName(), resetLink);
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            System.err.println("Failed to send password reset email: " + e.getMessage());
        }
    }

    /**
     * Send password changed notification
     */
    private void sendPasswordChangedNotification(User user) {
        try {
            String subject = "Password Reset Successful - GameHub";
            String body = """
                <!DOCTYPE html>
                <html>
                <body>
                    <h2>Password Reset Successful</h2>
                    <p>Hello %s,</p>
                    <p>Your password has been successfully reset.</p>
                    <p>You can now login with your new password.</p>
                    <p>If you didn't make this change, please contact support immediately.</p>
                </body>
                </html>
                """.formatted(user.getFullName());
            
            emailService.sendEmail(user.getEmail(), subject, body, true);
        } catch (Exception e) {
            System.err.println("Failed to send password changed notification: " + e.getMessage());
        }
    }

    /**
     * Generate random reset token
     */
    private String generateResetToken() {
        return java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    /**
     * Statistics DTO class
     */
    @Data
    @Builder
    public static class UserStatistics {
        private Double totalSpent;
        private Integer totalOrders;
        private Double averageOrderValue;
        private Long daysSinceJoin;
        private LocalDateTime lastOrderDate;
        private Map<String, Boolean> preferences;
    }
}