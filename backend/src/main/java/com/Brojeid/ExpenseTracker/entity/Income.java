package com.Brojeid.ExpenseTracker.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.Brojeid.ExpenseTracker.dto.IncomeDTO;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    
    public IncomeDTO getIncomeDTO() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setId(id);
        incomeDTO.setTitle(title);
        incomeDTO.setAmount(amount);
        incomeDTO.setDate(date);
        incomeDTO.setCategory(category);
        incomeDTO.setDecription(decription);
        return incomeDTO;
    }
}
