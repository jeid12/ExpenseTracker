package com.Brojeid.ExpenseTracker.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.Brojeid.ExpenseTracker.dto.ExpenseDTO;

@Entity
@Data
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String decription;
    
    private String category;

    private LocalDate date;

    private Integer amount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    public ExpenseDTO getExpenseDTO() {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setId(id);
        expenseDTO.setTitle(title);
        expenseDTO.setAmount(amount);
        expenseDTO.setDate(date);
        expenseDTO.setCategory(category);
        expenseDTO.setDecription(decription);
        return expenseDTO;
    }
}
