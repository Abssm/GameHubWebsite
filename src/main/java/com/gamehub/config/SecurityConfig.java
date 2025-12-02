package com.gamehub.config;

import com.gamehub.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http))
            .authorizeHttpRequests(auth -> {
                // permit H2 console explicitly using an Ant-style RequestMatcher to avoid servlet ambiguity
                auth.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
                // Public endpoints (no authentication required)
                auth.requestMatchers(new AntPathRequestMatcher("/")).permitAll();
                auth.requestMatchers(new AntPathRequestMatcher("/games/**")).permitAll();
                auth.requestMatchers(new AntPathRequestMatcher("/auth/**")).permitAll();
                auth.requestMatchers(new AntPathRequestMatcher("/index.html")).permitAll();
                auth.requestMatchers(new AntPathRequestMatcher("/login/oauth2/**")).permitAll();
                // Admin endpoints
                auth.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN");
                // All other requests require authentication
                auth.anyRequest().authenticated();
            })
            // Configure OAuth2 login
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/index.html")
                .defaultSuccessUrl("/index.html", true)
                .failureUrl("/index.html?error=true")
            )
            // allow frames (H2 console uses frames)
            .headers(headers -> headers.frameOptions().disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
    
    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository();
    }
}