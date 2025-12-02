package com.gamehub.controller;

import com.gamehub.dto.GameDto;
import com.gamehub.dto.UserDto;
import com.gamehub.service.AdminService;
import com.gamehub.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    
    private final AdminService adminService;
    private final GameService gameService;
    
    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getAnalytics() {
        return ResponseEntity.ok(adminService.getAnalytics());
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }
    
    @PutMapping("/users/{userId}/toggle-ban")
    public ResponseEntity<UserDto> toggleUserBan(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.toggleUserBan(userId));
    }
    
    @PutMapping("/users/{userId}/make-admin")
    public ResponseEntity<UserDto> makeAdmin(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.makeAdmin(userId));
    }
    
    @PostMapping("/games")
    public ResponseEntity<GameDto> addGame(@RequestBody GameDto gameDto) {
        return ResponseEntity.ok(gameService.addGame(gameDto));
    }
    
    @PutMapping("/games/{gameId}")
    public ResponseEntity<GameDto> updateGame(@PathVariable Long gameId, @RequestBody GameDto gameDto) {
        return ResponseEntity.ok(gameService.updateGame(gameId, gameDto));
    }
    
    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/categories/stats")
    public ResponseEntity<Map<String, Integer>> getCategoryStatistics() {
        return ResponseEntity.ok(adminService.getCategoryStatistics());
    }
}