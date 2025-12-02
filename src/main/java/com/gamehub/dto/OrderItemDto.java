package com.gamehub.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private GameDto game;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
