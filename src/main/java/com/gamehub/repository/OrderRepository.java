package com.gamehub.repository;

import com.gamehub.entity.Order;
import com.gamehub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    Optional<Order> findByOrderNumber(String orderNumber);
    
    // Count orders by user
    int countByUser(User user);
    
    // Find first order by user ordered by date
    Optional<Order> findFirstByUserOrderByOrderDateDesc(User user);
    
    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findRecentOrders();
    
    @Query("SELECT DATE(o.orderDate), SUM(o.total) FROM Order o " +
           "WHERE o.orderDate >= :startDate " +
           "GROUP BY DATE(o.orderDate)")
    List<Object[]> getDailyRevenue(LocalDateTime startDate);
    
    @Query("SELECT SUM(o.total) FROM Order o WHERE o.orderDate >= :startDate")
    Optional<Double> getTotalRevenue(LocalDateTime startDate);
}