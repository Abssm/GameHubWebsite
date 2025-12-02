package com.gamehub.controller;

import com.gamehub.dto.UserDto;
import com.gamehub.entity.User;
import com.gamehub.exception.GamehubException;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(@AuthenticationPrincipal Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        
        user.setFullName(userDto.getFullName());
        User updatedUser = userRepository.save(user);
        
        return ResponseEntity.ok(modelMapper.map(updatedUser, UserDto.class));
    }
    
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GamehubException("User not found"));
        return ResponseEntity.ok(modelMapper.map(user, UserDto.class));
    }
}