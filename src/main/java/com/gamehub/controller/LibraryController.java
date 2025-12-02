package com.gamehub.controller;

import com.gamehub.entity.DigitalCode;
import com.gamehub.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {
    
    private final LibraryService libraryService;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getLibrary(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(libraryService.getUserLibrary(userId));
    }
    
    @GetMapping("/codes")
    public ResponseEntity<List<DigitalCode>> getDigitalCodes(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(libraryService.getUserDigitalCodes(userId));
    }
    
    @GetMapping("/codes/unredeemed")
    public ResponseEntity<List<DigitalCode>> getUnredeemedCodes(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(libraryService.getUserUnredeemedCodes(userId));
    }
    
    @PostMapping("/codes/redeem/{code}")
    public ResponseEntity<DigitalCode> redeemCode(
            @AuthenticationPrincipal Long userId,
            @PathVariable String code) {
        return ResponseEntity.ok(libraryService.redeemCode(userId, code));
    }
}