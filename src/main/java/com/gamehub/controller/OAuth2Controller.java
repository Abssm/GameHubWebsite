package com.gamehub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/callback")
    public ResponseEntity<?> oauth2Callback(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            OAuth2User oauth2User = oauth2Token.getPrincipal();
            
            // Get the authorized client
            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                    .loadAuthorizedClient(oauth2Token.getAuthorizedClientRegistrationId(), 
                                         oauth2Token.getName());
            
            // Log user information
            log.info("User authenticated via OAuth2: {}", oauth2User.getAttributes());
            
            // Redirect to home page or dashboard
            try {
                response.sendRedirect("/index.html");
            } catch (IOException e) {
                log.error("Error redirecting user: {}", e.getMessage());
                return ResponseEntity.ok("Authentication successful. Please go to <a href='/index.html'>homepage</a>");
            }
            
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.badRequest().body("Authentication failed");
    }

    @GetMapping("/login/oauth2/cognito")
    public ResponseEntity<?> loginWithCognito() {
        // This endpoint will be automatically handled by Spring Security OAuth2
        // and will redirect to Cognito login page
        return ResponseEntity.ok().build();
    }
}