package com.gamehub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequest {
    
    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;
    
    @NotBlank(message = "Expiry date is required")
    @Pattern(regexp = "(0[1-9]|1[0-2])/[0-9]{2}", message = "Expiry date must be in MM/YY format")
    private String expiryDate;
    
    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;
    
    @NotBlank(message = "Cardholder name is required")
    @Size(min = 3, max = 50, message = "Cardholder name must be between 3 and 50 characters")
    private String cardholderName;
}