package com.gamehub.repository;

import com.gamehub.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Search by email or name
    List<User> findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(String email, String name);

    // Find by join date range
    List<User> findByJoinDateBetween(LocalDateTime start, LocalDateTime end);

    // Find top spenders
    @Query("SELECT u FROM User u ORDER BY u.totalSpent DESC")
    List<User> findTopSpenders(Pageable pageable);

    // Find by reset token
    Optional<User> findByResetToken(String token);

    // Count by join date after
    Long countByJoinDateAfter(LocalDateTime date);
}