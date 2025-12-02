package com.gamehub.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class GameDto {
    private Long id;
    private String title;
    private String category;
    private BigDecimal priceUSD;
    private BigDecimal priceSAR;
    private String imageUrl;
    private String description;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;
    private String rating;
    private List<String> platforms;
    private List<String> tags;
    private boolean featured;
    private Integer stock;
    private Integer salesCount;
}