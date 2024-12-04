package com.ramsa.company.controller;

import com.ramsa.company.dto.AuthResponse;
import com.ramsa.company.dto.LoginRequest;
import com.ramsa.company.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ResponseEntity<?> getServiceInfo() {
        return ResponseEntity.ok("GET request handled successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            // Send both tokens to the client
            return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        try {
            // Validate the refresh token
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
            if (jwtTokenProvider.validateToken(refreshToken)) {
                // Generate new access token and refresh token
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, null));

                String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
                String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

                return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
