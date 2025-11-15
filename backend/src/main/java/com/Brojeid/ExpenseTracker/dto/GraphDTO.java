package com.Brojeid.ExpenseTracker.dto;
import lombok.Data;

import java.util.List;

import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.Income;

@Data 
public class GraphDTO {

    private List<Expense> expensesList;

    private List<Income> incomesList;

}
