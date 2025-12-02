package com.gamehub.repository;

import com.gamehub.entity.PurchaseHistory;
import com.gamehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    List<PurchaseHistory> findByUserOrderByPurchaseDateDesc(User user);
}