package com.Brojeid.ExpenseTracker.dto;

import com.Brojeid.ExpenseTracker.entity.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDTO {
    
    private Long id;
    
    @NotBlank(message = "Category name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Category type is required")
    private CategoryType type;
    
    private Boolean isActive = true;
}
