package com.gamehub.service;

import com.gamehub.dto.PaymentRequest;
import com.gamehub.entity.Order;
import com.gamehub.entity.Payment;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.PaymentRepository;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EmailService emailService;

    /**
     * Process a payment for an order
     */
    @Transactional
    public Payment processPayment(Order order, PaymentRequest paymentRequest) {
        try {
            // Validate payment details
            validatePaymentDetails(paymentRequest);

            // Create payment record
            Payment payment = createPaymentRecord(order, paymentRequest);

            // Simulate payment gateway processing
            boolean paymentSuccessful = simulatePaymentGateway(paymentRequest);
            
            if (!paymentSuccessful) {
                throw new GamehubException("Payment processing failed. Please check your payment details.");
            }

            Payment savedPayment = paymentRepository.save(payment);
            
            // Send payment confirmation email
            sendPaymentConfirmationEmail(order, savedPayment);
            
            log.info("Payment processed successfully for order: {}", order.getOrderNumber());
            return savedPayment;

        } catch (Exception e) {
            log.error("Payment processing failed for order: {}", order.getOrderNumber(), e);
            throw new GamehubException("Payment processing failed: " + e.getMessage());
        }
    }

    /**
     * Validate payment details
     */
    private void validatePaymentDetails(PaymentRequest paymentRequest) {
        // Validate card number (Luhn algorithm)
        if (!isValidCardNumber(paymentRequest.getCardNumber())) {
            throw new GamehubException("Invalid card number");
        }

        // Validate expiry date
        if (!isValidExpiryDate(paymentRequest.getExpiryDate())) {
            throw new GamehubException("Invalid or expired card");
        }

        // Validate CVV
        if (!isValidCVV(paymentRequest.getCvv())) {
            throw new GamehubException("Invalid CVV");
        }

        // Validate cardholder name
        if (paymentRequest.getCardholderName() == null || 
            paymentRequest.getCardholderName().trim().length() < 3) {
            throw new GamehubException("Invalid cardholder name");
        }
    }

    /**
     * Create payment record
     */
    private Payment createPaymentRecord(Order order, PaymentRequest paymentRequest) {
        String maskedCardNumber = maskCardNumber(paymentRequest.getCardNumber());
        String lastFourDigits = paymentRequest.getCardNumber().substring(
                paymentRequest.getCardNumber().length() - 4);

        return Payment.builder()
                .order(order)
                .paymentMethod("CREDIT_CARD")
                .cardLastFour(lastFourDigits)
                .maskedCardNumber(maskedCardNumber)
                .transactionId(generateTransactionId())
                .amount(order.getTotal())
                .currency(order.getCurrency())
                .status("PROCESSING")
                .paymentDate(LocalDateTime.now())
                .build();
    }

    /**
     * Simulate payment gateway (for demo purposes)
     * In production, integrate with real payment gateway like Stripe, PayPal, etc.
     */
    private boolean simulatePaymentGateway(PaymentRequest paymentRequest) {
        try {
            // Simulate network delay
            Thread.sleep(500);
            
            // For demo: accept all payments except specific test card numbers
            String cardNumber = paymentRequest.getCardNumber().replaceAll("\\s", "");
            
            // Test card numbers that will fail
            String[] declinedCards = {
                "4111111111111112", // Test decline card
                "4000000000000002"  // Another test decline card
            };
            
            for (String declinedCard : declinedCards) {
                if (cardNumber.startsWith(declinedCard.substring(0, 6))) {
                    return false; // Simulate declined card
                }
            }
            
            return true; // Payment successful
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Send payment confirmation email
     */
    private void sendPaymentConfirmationEmail(Order order, Payment payment) {
        try {
            String subject = "Payment Confirmation - Order #" + order.getOrderNumber();
            String body = buildPaymentConfirmationEmail(order, payment);
            
            emailService.sendEmail(
                order.getUser().getEmail(),
                subject,
                body,
                true // HTML content
            );
        } catch (Exception e) {
            log.warn("Failed to send payment confirmation email: {}", e.getMessage());
        }
    }

    /**
     * Build payment confirmation email content
     */
    private String buildPaymentConfirmationEmail(Order order, Payment payment) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background: linear-gradient(90deg, #ff0000, #ff6b6b); color: white; padding: 20px; text-align: center; border-radius: 10px 10px 0 0; }
                    .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                    .receipt { background: white; padding: 20px; border-radius: 8px; margin: 20px 0; border: 1px solid #ddd; }
                    .footer { text-align: center; margin-top: 30px; color: #666; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>GameHub - Payment Confirmation</h1>
                    </div>
                    <div class="content">
                        <h2>Thank you for your purchase!</h2>
                        <p>Your payment has been processed successfully.</p>
                        
                        <div class="receipt">
                            <h3>Payment Details</h3>
                            <p><strong>Order Number:</strong> %s</p>
                            <p><strong>Transaction ID:</strong> %s</p>
                            <p><strong>Payment Method:</strong> %s</p>
                            <p><strong>Card:</strong> **** **** **** %s</p>
                            <p><strong>Amount Paid:</strong> %s%.2f</p>
                            <p><strong>Date:</strong> %s</p>
                        </div>
                        
                        <p>Your game keys have been generated and are available in your GameHub library.</p>
                        <p><a href="http://localhost:5500/#library" style="background: #ff0000; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block;">Access Your Library</a></p>
                        
                        <p>If you have any questions, please contact our support team.</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 GameHub. All rights reserved.</p>
                        <p>This is an automated email, please do not reply.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                order.getOrderNumber(),
                payment.getTransactionId(),
                payment.getPaymentMethod(),
                payment.getCardLastFour(),
                order.getCurrency().equals("USD") ? "$" : "ر.س",
                payment.getAmount().doubleValue(),
                payment.getPaymentDate().toString()
            );
    }

    /**
     * Get payment by transaction ID
     */
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new GamehubException("Payment not found"));
    }

    /**
     * Get payments for an order
     */
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new GamehubException("Payment not found for this order"));
    }

    /**
     * Refund a payment
     */
    @Transactional
    public Payment refundPayment(Long paymentId, BigDecimal amount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new GamehubException("Payment not found"));

        if (!payment.getStatus().equals("COMPLETED")) {
            throw new GamehubException("Only completed payments can be refunded");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0 || 
            amount.compareTo(payment.getAmount()) > 0) {
            throw new GamehubException("Invalid refund amount");
        }

        payment.setStatus("REFUNDED");
        payment.setRefundAmount(amount);
        payment.setRefundDate(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    /**
     * Validate card number using Luhn algorithm
     */
    private boolean isValidCardNumber(String cardNumber) {
        String cleaned = cardNumber.replaceAll("\\s", "");
        
        if (!cleaned.matches("\\d{12,19}")) {
            return false;
        }

        // Luhn algorithm
        int sum = 0;
        boolean alternate = false;
        for (int i = cleaned.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cleaned.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    /**
     * Validate expiry date
     */
    private boolean isValidExpiryDate(String expiryDate) {
        if (!expiryDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            return false;
        }

        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt("20" + parts[1]); // Convert YY to YYYY

        LocalDateTime now = LocalDateTime.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        if (year < currentYear) {
            return false;
        }
        if (year == currentYear && month < currentMonth) {
            return false;
        }
        return month >= 1 && month <= 12;
    }

    /**
     * Validate CVV
     */
    private boolean isValidCVV(String cvv) {
        return cvv.matches("\\d{3,4}");
    }

    /**
     * Mask card number for display
     */
    private String maskCardNumber(String cardNumber) {
        String cleaned = cardNumber.replaceAll("\\s", "");
        if (cleaned.length() <= 4) {
            return "****";
        }
        return "**** **** **** " + cleaned.substring(cleaned.length() - 4);
    }

    /**
     * Generate unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    /**
     * Get payment statistics for admin dashboard
     */
    public PaymentStatistics getPaymentStatistics() {
        PaymentStatistics stats = PaymentStatistics.builder()
                .paymentMethodDistribution(new HashMap<>())
                .build();
        
        // Total revenue
        BigDecimal totalRevenue = paymentRepository.sumCompletedPayments();
        stats.setTotalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO);
        
        // Today's revenue
        BigDecimal todayRevenue = paymentRepository.sumTodayRevenue();
        stats.setTodayRevenue(todayRevenue != null ? todayRevenue : BigDecimal.ZERO);
        
        // Monthly revenue
        BigDecimal monthlyRevenue = paymentRepository.sumMonthlyRevenue();
        stats.setMonthlyRevenue(monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO);
        
        // Payment methods distribution
        List<Object[]> methodDistribution = paymentRepository.getPaymentMethodDistribution();
        Map<String, Long> distribution = new HashMap<>();
        for (Object[] row : methodDistribution) {
            distribution.put((String) row[0], (Long) row[1]);
        }
        stats.setPaymentMethodDistribution(distribution);
        
        return stats;
    }

    /**
     * Statistics DTO class
     */
    @Data
    @Builder
    public static class PaymentStatistics {
        private BigDecimal totalRevenue;
        private BigDecimal todayRevenue;
        private BigDecimal monthlyRevenue;
        private Map<String, Long> paymentMethodDistribution;
        private Integer totalTransactions;
    }
}