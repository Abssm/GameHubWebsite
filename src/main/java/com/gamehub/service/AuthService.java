package com.gamehub.service;

import com.gamehub.dto.AuthRequest;
import com.gamehub.dto.AuthResponse;
import com.gamehub.dto.UserDto;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.UserRepository;
import com.gamehub.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthResponse register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new GamehubException("Email already registered");
        }
        
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .fullName(userDto.getFullName())
                .isAdmin(userDto.getEmail().toLowerCase().contains("admin"))
                .build();
        
        userRepository.save(user);
        
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .isAdmin(user.isAdmin())
                .userId(user.getId())
                .build();
    }
    
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new GamehubException("User not found"));
        
        if (user.isBanned()) {
            throw new GamehubException("Account is banned. Contact support.");
        }
        
        String token = jwtService.generateToken(user);
        
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .isAdmin(user.isAdmin())
                .userId(user.getId())
                .build();
    }
}