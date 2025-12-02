package com.gamehub.controller;

import com.gamehub.dto.GameDto;
import com.gamehub.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {
    
    private final GameService gameService;
    
    @GetMapping
    public ResponseEntity<List<GameDto>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<GameDto>> getFeaturedGames() {
        return ResponseEntity.ok(gameService.getFeaturedGames());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<GameDto>> getGamesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(gameService.getGamesByCategory(category));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<GameDto>> searchGames(@RequestParam String query) {
        return ResponseEntity.ok(gameService.searchGames(query));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }
    
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Integer>> getCategoryCounts() {
        return ResponseEntity.ok(gameService.getCategoryCounts());
    }
    
    @GetMapping("/top-selling")
    public ResponseEntity<List<GameDto>> getTopSellingGames() {
        return ResponseEntity.ok(gameService.getTopSellingGames());
    }
}