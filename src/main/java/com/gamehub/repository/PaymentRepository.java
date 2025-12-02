package com.gamehub.repository;

import com.gamehub.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
// Add these methods to PaymentRepository interface:

// Find by transaction ID
Optional<Payment> findByTransactionId(String transactionId);

// Find by order ID
Optional<Payment> findByOrderId(Long orderId);

// Sum completed payments
@Query("SELECT SUM(p.amount) FROM Payment p WHERE p.status = 'COMPLETED'")
BigDecimal sumCompletedPayments();

// Sum today's revenue
@Query("SELECT SUM(p.amount) FROM Payment p WHERE DATE(p.paymentDate) = CURRENT_DATE AND p.status = 'COMPLETED'")
BigDecimal sumTodayRevenue();

// Sum monthly revenue
@Query("SELECT SUM(p.amount) FROM Payment p WHERE YEAR(p.paymentDate) = YEAR(CURRENT_DATE) AND MONTH(p.paymentDate) = MONTH(CURRENT_DATE) AND p.status = 'COMPLETED'")
BigDecimal sumMonthlyRevenue();

// Payment method distribution
@Query("SELECT p.paymentMethod, COUNT(p) FROM Payment p GROUP BY p.paymentMethod")
List<Object[]> getPaymentMethodDistribution();
}