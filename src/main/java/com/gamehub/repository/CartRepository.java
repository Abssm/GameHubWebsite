package com.gamehub.repository;

import com.gamehub.entity.Cart;
import com.gamehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndGameId(User user, Long gameId);
    void deleteByUser(User user);
    Integer countByUser(User user);
}