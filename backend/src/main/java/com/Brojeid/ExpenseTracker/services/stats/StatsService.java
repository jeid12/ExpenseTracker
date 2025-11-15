package com.Brojeid.ExpenseTracker.services.stats;

import com.Brojeid.ExpenseTracker.dto.GraphDTO;
import com.Brojeid.ExpenseTracker.dto.StatsDTO;


public interface StatsService {

GraphDTO getChartData();

StatsDTO getStats();
}
