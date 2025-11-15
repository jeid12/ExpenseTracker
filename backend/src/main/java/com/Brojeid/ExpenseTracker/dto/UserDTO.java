package com.Brojeid.ExpenseTracker.dto;

import java.time.LocalDateTime;

import com.Brojeid.ExpenseTracker.entity.Role;

import lombok.Data;

@Data
public class UserDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String profileImageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
