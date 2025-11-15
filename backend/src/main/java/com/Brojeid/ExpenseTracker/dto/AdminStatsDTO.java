package com.Brojeid.ExpenseTracker.dto;

import lombok.Data;

@Data
public class AdminStatsDTO {
    private Long totalUsers;
    private Long activeUsers;
    private Long inactiveUsers;
    private Double totalSystemIncome;
    private Double totalSystemExpense;
    private Double systemBalance;
    private Long totalExpenseRecords;
    private Long totalIncomeRecords;
    private Long totalCategories;
    private Long activeCategories;
}
