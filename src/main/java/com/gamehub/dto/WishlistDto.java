package com.gamehub.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WishlistDto {
    private Long id;
    private Long gameId;
    private String gameTitle;
    private String gameImage;
    private String category;
    private Double price;
    private LocalDateTime addedDate;
}