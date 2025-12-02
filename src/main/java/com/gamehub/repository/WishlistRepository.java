package com.gamehub.repository;

import com.gamehub.entity.User;
import com.gamehub.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
    Optional<Wishlist> findByUserAndGameId(User user, Long gameId);
    boolean existsByUserAndGameId(User user, Long gameId);
}