package com.Brojeid.ExpenseTracker.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReportRequestDTO {
    private LocalDate startDate;
    private LocalDate endDate;
}
