package com.Brojeid.ExpenseTracker.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExpenseDTO {
    private Long id;
   
    private String title; 

    private String decription;
    
    private String category;

    private LocalDate date;

    private Integer amount;
}
