package com.Brojeid.ExpenseTracker.services.stats;

import java.time.LocalDate;

import com.Brojeid.ExpenseTracker.dto.AdminStatsDTO;
import com.Brojeid.ExpenseTracker.dto.GraphDTO;
import com.Brojeid.ExpenseTracker.dto.ReportDTO;
import com.Brojeid.ExpenseTracker.dto.StatsDTO;


public interface StatsService {

GraphDTO getChartData();

StatsDTO getStats();

AdminStatsDTO getAdminStats();

ReportDTO getUserReport(LocalDate startDate, LocalDate endDate);

ReportDTO getSystemReport(LocalDate startDate, LocalDate endDate);
}
