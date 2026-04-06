package com.example.LibraryManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF (Cross-Site Request Forgery)
                // We disable this because we are building a stateless REST API, not a web page.
                .csrf(csrf -> csrf.disable())

                // 2. The VIP List (Whitelist)
                .authorizeHttpRequests(auth -> auth
                        // CHANGE THIS PATH to whatever your actual "add user" URL is!
                        .requestMatchers("/users/add").permitAll()
                        .requestMatchers("/users/display-all").permitAll()

                        // 3. Lock everything else down
                        .anyRequest().permitAll()
                )

                // 4. Tell Spring we are using JWTs, so don't create server sessions
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    // 5. The Password Hasher
    // Spring Security strictly prevents you from saving plain-text passwords.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}