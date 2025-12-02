package com.gamehub.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Long id;
    private String paymentMethod;
    private String cardLastFour;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private LocalDateTime paymentDate;
}
