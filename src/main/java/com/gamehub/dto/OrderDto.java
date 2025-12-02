package com.gamehub.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String orderNumber;
    private List<OrderItemDto> items;
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal total;
    private String currency;
    private String status;
    private LocalDateTime orderDate;
    private PaymentDto payment;
}