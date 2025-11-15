package com.Brojeid.ExpenseTracker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Brojeid.ExpenseTracker.config.JwtUtil;
import com.Brojeid.ExpenseTracker.dto.ForgotPasswordRequest;
import com.Brojeid.ExpenseTracker.dto.LoginRequest;
import com.Brojeid.ExpenseTracker.dto.LoginResponse;
import com.Brojeid.ExpenseTracker.dto.RegisterRequest;
import com.Brojeid.ExpenseTracker.dto.ResetPasswordRequest;
import com.Brojeid.ExpenseTracker.dto.UserDTO;
import com.Brojeid.ExpenseTracker.services.email.EmailService;
import com.Brojeid.ExpenseTracker.services.user.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserDTO userDTO = userService.registerUser(request);
            
            // Send welcome email
            try {
                emailService.sendWelcomeEmail(userDTO.getEmail(), userDTO.getFirstName());
            } catch (Exception e) {
                // Log error but don't fail registration
                System.err.println("Failed to send welcome email: " + e.getMessage());
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("user", userDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDTO userDTO = userService.getUserByEmail(userDetails.getUsername());
            
            // Add role to JWT claims
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", userDTO.getRole().name());
            claims.put("userId", userDTO.getId());
            
            String token = jwtUtil.generateToken(userDetails, claims);
            LoginResponse response = new LoginResponse(token, userDTO);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            String token = userService.generatePasswordResetToken(request.getEmail());
            emailService.sendPasswordResetEmail(request.getEmail(), token);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset email sent successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            userService.resetPassword(request.getToken(), request.getNewPassword());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Password reset successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
