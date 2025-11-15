package com.Brojeid.ExpenseTracker.repository;

import com.Brojeid.ExpenseTracker.entity.Category;
import com.Brojeid.ExpenseTracker.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    List<Category> findByIsActiveTrue();
    List<Category> findByTypeAndIsActiveTrue(CategoryType type);
    Long countByIsActive(Boolean isActive);
}
