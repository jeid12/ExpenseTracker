package com.Brojeid.ExpenseTracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Brojeid.ExpenseTracker.dto.CategoryDTO;
import com.Brojeid.ExpenseTracker.dto.UserDTO;
import com.Brojeid.ExpenseTracker.dto.AdminStatsDTO;
import com.Brojeid.ExpenseTracker.dto.ReportDTO;
import com.Brojeid.ExpenseTracker.dto.ReportRequestDTO;
import com.Brojeid.ExpenseTracker.services.category.CategoryService;
import com.Brojeid.ExpenseTracker.services.user.UserService;
import com.Brojeid.ExpenseTracker.services.stats.StatsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private StatsService statsService;

    // Admin Dashboard Stats
    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDTO> getAdminStats() {
        AdminStatsDTO stats = statsService.getAdminStats();
        return ResponseEntity.ok(stats);
    }
    
    // Admin System Report
    @PostMapping("/reports/system")
    public ResponseEntity<ReportDTO> getSystemReport(@RequestBody ReportRequestDTO request) {
        ReportDTO report = statsService.getSystemReport(request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(report);
    }
    
    // Admin Personal Report
    @PostMapping("/reports/personal")
    public ResponseEntity<ReportDTO> getAdminPersonalReport(@RequestBody ReportRequestDTO request) {
        ReportDTO report = statsService.getUserReport(request.getStartDate(), request.getEndDate());
        return ResponseEntity.ok(report);
    }

    // User Management
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/users/{id}/toggle-status")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.toggleUserStatus(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User status updated successfully");
            response.put("user", userDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    // Category Management
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDTO category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category created successfully");
            response.put("category", createdCategory);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category updated successfully");
            response.put("category", updatedCategory);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/categories/{id}/toggle-status")
    public ResponseEntity<?> toggleCategoryStatus(@PathVariable Long id) {
        try {
            CategoryDTO categoryDTO = categoryService.toggleCategoryStatus(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Category status updated successfully");
            response.put("category", categoryDTO);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
