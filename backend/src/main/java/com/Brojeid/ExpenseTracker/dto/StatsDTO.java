package com.Brojeid.ExpenseTracker.dto;

import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.Income;

import lombok.Data;

@Data
public class StatsDTO {

    private double income;
    private double expense;

    private Income latestIncome;
    private Expense latestExpense;

    private Double balance;

    private Double minIncome;

    private Double maxIncome;

    private Double minExpense;

    private Double maxExpense;

}
