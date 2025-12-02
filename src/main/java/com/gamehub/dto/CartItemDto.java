package com.gamehub.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long gameId;
    private String gameTitle;
    private String gameImage;
    private String category;
    private Double price;
    private Integer quantity;
    private Double total;
}