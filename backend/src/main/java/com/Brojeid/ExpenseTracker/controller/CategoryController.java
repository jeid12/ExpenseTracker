package com.Brojeid.ExpenseTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Brojeid.ExpenseTracker.dto.CategoryDTO;
import com.Brojeid.ExpenseTracker.entity.CategoryType;
import com.Brojeid.ExpenseTracker.services.category.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getActiveCategories() {
        List<CategoryDTO> categories = categoryService.getActiveCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/by-type")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByType(@RequestParam CategoryType type) {
        List<CategoryDTO> categories = categoryService.getCategoriesByType(type);
        return ResponseEntity.ok(categories);
    }
}
