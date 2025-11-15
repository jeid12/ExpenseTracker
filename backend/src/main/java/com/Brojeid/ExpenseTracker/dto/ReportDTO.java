package com.Brojeid.ExpenseTracker.dto;

import java.util.Map;
import lombok.Data;

@Data
public class ReportDTO {
    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
    private Long incomeCount;
    private Long expenseCount;
    private Double averageIncome;
    private Double averageExpense;
    private Map<String, Double> incomeByCategory;
    private Map<String, Double> expenseByCategory;
    private String reportPeriod;
}
