package com.Brojeid.ExpenseTracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.entity.User;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(i.amount) FROM Income i")
    Double sumAllAmounts();
    
    Optional<Income> findFirstByOrderByDateDesc();
    
    // User-specific queries
    List<Income> findByUser(User user);
    
    List<Income> findByUserOrderByDateDesc(User user);
    
    List<Income> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user = :user")
    Double sumAmountsByUser(@Param("user") User user);
    
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.user = :user AND i.date BETWEEN :startDate AND :endDate")
    Double sumAmountsByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Count queries
    @Query("SELECT COUNT(i) FROM Income i WHERE i.user = :user")
    Long countByUser(@Param("user") User user);
    
    @Query("SELECT COUNT(i) FROM Income i WHERE i.user = :user AND i.date BETWEEN :startDate AND :endDate")
    Long countByUserAndDateBetween(@Param("user") User user, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // System-wide queries for admin
    @Query("SELECT COUNT(i) FROM Income i")
    Long countAll();
    
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.date BETWEEN :startDate AND :endDate")
    Double sumAmountsByDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

