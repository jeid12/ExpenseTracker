package com.Brojeid.ExpenseTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Brojeid.ExpenseTracker.entity.Income;

@Repository
public interface IncomeRepository  extends JpaRepository<Income, Long> {

}
