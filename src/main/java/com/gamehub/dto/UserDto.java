package com.gamehub.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private LocalDateTime joinDate;
    private boolean isAdmin;
    private boolean isBanned;
    private Double totalSpent;
    private Integer orderCount;
}