package com.gamehub.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserUpdateRequest {
    private String fullName;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String preferredLanguage;
    private String preferredCurrency;
    private Map<String, Boolean> notificationPreferences;
    private String theme;
}