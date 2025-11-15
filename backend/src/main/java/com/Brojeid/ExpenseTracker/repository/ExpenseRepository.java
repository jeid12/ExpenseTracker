package com.Brojeid.ExpenseTracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.User;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double sumAllAmounts();
    
    Optional<Expense> findFirstByOrderByDateDesc();
    
    // User-specific queries
    List<Expense> findByUser(User user);
    
    List<Expense> findByUserOrderByDateDesc(User user);
    
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user")
    Double sumAmountsByUser(@Param("user") User user);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate")
    Double sumAmountsByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Count queries
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user = :user")
    Long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(e) FROM Expense e WHERE e.user = :user AND e.date BETWEEN :startDate AND :endDate")
    Long countByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // System-wide queries for admin
    @Query("SELECT COUNT(e) FROM Expense e")
    Long countAll();
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    Double sumAmountsByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

