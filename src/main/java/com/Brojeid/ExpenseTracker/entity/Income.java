package com.Brojeid.ExpenseTracker.entity;

import java.time.LocalDate;

import com.Brojeid.ExpenseTracker.dto.IncomeDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String title;

private String decription;

private String category;

private LocalDate date;

private Integer amount;

public IncomeDTO  getIncomeDTO() {
    IncomeDTO dto = new IncomeDTO();
    dto.setId(id);
    dto.setTitle(title);
    dto.setDecription(decription);
    dto.setCategory(category);
    dto.setDate(date);
    dto.setAmount(amount);
    return dto;
}
}
