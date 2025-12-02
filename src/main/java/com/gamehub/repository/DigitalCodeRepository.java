package com.gamehub.repository;

import com.gamehub.entity.DigitalCode;
import com.gamehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DigitalCodeRepository extends JpaRepository<DigitalCode, Long> {
    List<DigitalCode> findByUser(User user);
    List<DigitalCode> findByUserAndRedeemedFalse(User user);
    Optional<DigitalCode> findByCode(String code);
}