package com.gamehub.controller;

import com.gamehub.dto.AuthRequest;
import com.gamehub.dto.AuthResponse;
import com.gamehub.dto.UserDto;
import com.gamehub.service.AuthService;
import com.gamehub.service.CognitoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminInitiateAuthResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final CognitoService cognitoService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserDto userDto) {
        // First register with Cognito
        boolean cognitoSuccess = cognitoService.signUpUser(userDto.getEmail(), userDto.getPassword(), userDto.getFullName());
        
        if (!cognitoSuccess) {
            return ResponseEntity.badRequest().build();
        }
        
        // Then register with local database
        return ResponseEntity.ok(authService.register(userDto));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        try {
            // First authenticate with Cognito
            AdminInitiateAuthResponse authResult = cognitoService.authenticateUser(request.getEmail(), request.getPassword());
            
            // Extract token from Cognito response
            String accessToken = authResult.authenticationResult().accessToken();
            
            // Then login with local database
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
    
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok("Token is valid");
    }
}