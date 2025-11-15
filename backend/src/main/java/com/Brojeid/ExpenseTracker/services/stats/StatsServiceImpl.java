package com.Brojeid.ExpenseTracker.services.stats;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.AdminStatsDTO;
import com.Brojeid.ExpenseTracker.dto.GraphDTO;
import com.Brojeid.ExpenseTracker.dto.ReportDTO;
import com.Brojeid.ExpenseTracker.dto.StatsDTO;
import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.entity.User;
import com.Brojeid.ExpenseTracker.repository.CategoryRepository;
import com.Brojeid.ExpenseTracker.repository.ExpenseRepository;
import com.Brojeid.ExpenseTracker.repository.IncomeRepository;
import com.Brojeid.ExpenseTracker.repository.UserRepository;
import com.Brojeid.ExpenseTracker.services.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserEntityByEmail(email);
    }

    public GraphDTO getChartData(){
        User currentUser = getCurrentUser();
        LocalDate enDate = LocalDate.now();
        LocalDate starDate = enDate.minusDays(27);

        GraphDTO graphDTO = new GraphDTO();
        
        graphDTO.setExpensesList(expenseRepository.findByUserAndDateBetween(currentUser, starDate, enDate));
        graphDTO.setIncomesList(incomeRepository.findByUserAndDateBetween(currentUser, starDate, enDate));
         
        return graphDTO;
    }

    public StatsDTO getStats(){
        User currentUser = getCurrentUser();
        Double totalIncome = incomeRepository.sumAmountsByUser(currentUser);
        Double totalExpense = expenseRepository.sumAmountsByUser(currentUser);

        List<Income> incomeList = incomeRepository.findByUser(currentUser);
        List<Expense> expenseList = expenseRepository.findByUser(currentUser);

        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setIncome(totalIncome != null ? totalIncome : 0.0);
        statsDTO.setExpense(totalExpense != null ? totalExpense : 0.0);

        if (!incomeList.isEmpty()) {
            statsDTO.setLatestIncome(incomeList.get(0));
        }
        
        if (!expenseList.isEmpty()) {
            statsDTO.setLatestExpense(expenseList.get(0));
        }

        statsDTO.setBalance((totalIncome != null ? totalIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0));

        OptionalDouble minIncome = incomeList.stream().mapToDouble(Income::getAmount).min(); 
        OptionalDouble maxIncome = incomeList.stream().mapToDouble(Income::getAmount).max();

        OptionalDouble minExpense = expenseList.stream().mapToDouble(Expense::getAmount).min();
        OptionalDouble maxExpense = expenseList.stream().mapToDouble(Expense::getAmount).max();

        statsDTO.setMaxExpense(maxExpense.isPresent() ? maxExpense.getAsDouble() : null);
        statsDTO.setMinExpense(minExpense.isPresent() ? minExpense.getAsDouble() : null);
        statsDTO.setMaxIncome(maxIncome.isPresent() ? maxIncome.getAsDouble() : null);
        statsDTO.setMinIncome(minIncome.isPresent() ? minIncome.getAsDouble() : null);

        return statsDTO; 
    }
    
    @Override
    public AdminStatsDTO getAdminStats() {
        AdminStatsDTO stats = new AdminStatsDTO();
        
        // User statistics
        stats.setTotalUsers(userRepository.count());
        stats.setActiveUsers(userRepository.countByIsActive(true));
        stats.setInactiveUsers(userRepository.countByIsActive(false));
        
        // Financial statistics
        Double totalIncome = incomeRepository.sumAllAmounts();
        Double totalExpense = expenseRepository.sumAllAmounts();
        stats.setTotalSystemIncome(totalIncome != null ? totalIncome : 0.0);
        stats.setTotalSystemExpense(totalExpense != null ? totalExpense : 0.0);
        stats.setSystemBalance((totalIncome != null ? totalIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0));
        
        // Record counts
        stats.setTotalExpenseRecords(expenseRepository.countAll());
        stats.setTotalIncomeRecords(incomeRepository.countAll());
        
        // Category statistics
        stats.setTotalCategories(categoryRepository.count());
        stats.setActiveCategories(categoryRepository.countByIsActive(true));
        
        return stats;
    }
    
    @Override
    public ReportDTO getUserReport(LocalDate startDate, LocalDate endDate) {
        User currentUser = getCurrentUser();
        return generateReport(currentUser, startDate, endDate);
    }
    
    @Override
    public ReportDTO getSystemReport(LocalDate startDate, LocalDate endDate) {
        return generateSystemReport(startDate, endDate);
    }
    
    private ReportDTO generateReport(User user, LocalDate startDate, LocalDate endDate) {
        ReportDTO report = new ReportDTO();
        
        // Get income and expense data
        List<Income> incomes = incomeRepository.findByUserAndDateBetween(user, startDate, endDate);
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, startDate, endDate);
        
        // Calculate totals
        Double totalIncome = incomeRepository.sumAmountsByUserAndDateBetween(user, startDate, endDate);
        Double totalExpense = expenseRepository.sumAmountsByUserAndDateBetween(user, startDate, endDate);
        
        report.setTotalIncome(totalIncome != null ? totalIncome : 0.0);
        report.setTotalExpense(totalExpense != null ? totalExpense : 0.0);
        report.setBalance((totalIncome != null ? totalIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0));
        
        // Calculate counts
        report.setIncomeCount(incomeRepository.countByUserAndDateBetween(user, startDate, endDate));
        report.setExpenseCount(expenseRepository.countByUserAndDateBetween(user, startDate, endDate));
        
        // Calculate averages
        report.setAverageIncome(report.getIncomeCount() > 0 ? report.getTotalIncome() / report.getIncomeCount() : 0.0);
        report.setAverageExpense(report.getExpenseCount() > 0 ? report.getTotalExpense() / report.getExpenseCount() : 0.0);
        
        // Group by category
        Map<String, Double> incomeByCategory = incomes.stream()
            .collect(Collectors.groupingBy(
                Income::getCategory,
                Collectors.summingDouble(Income::getAmount)
            ));
        
        Map<String, Double> expenseByCategory = expenses.stream()
            .collect(Collectors.groupingBy(
                Expense::getCategory,
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        report.setIncomeByCategory(incomeByCategory);
        report.setExpenseByCategory(expenseByCategory);
        report.setReportPeriod(startDate + " to " + endDate);
        
        return report;
    }
    
    private ReportDTO generateSystemReport(LocalDate startDate, LocalDate endDate) {
        ReportDTO report = new ReportDTO();
        
        // Get all income and expense data for the period
        List<Income> incomes = incomeRepository.findByDateBetween(startDate, endDate);
        List<Expense> expenses = expenseRepository.findByDateBetween(startDate, endDate);
        
        // Calculate totals
        Double totalIncome = incomeRepository.sumAmountsByDateBetween(startDate, endDate);
        Double totalExpense = expenseRepository.sumAmountsByDateBetween(startDate, endDate);
        
        report.setTotalIncome(totalIncome != null ? totalIncome : 0.0);
        report.setTotalExpense(totalExpense != null ? totalExpense : 0.0);
        report.setBalance((totalIncome != null ? totalIncome : 0.0) - (totalExpense != null ? totalExpense : 0.0));
        
        // Calculate counts
        report.setIncomeCount((long) incomes.size());
        report.setExpenseCount((long) expenses.size());
        
        // Calculate averages
        report.setAverageIncome(report.getIncomeCount() > 0 ? report.getTotalIncome() / report.getIncomeCount() : 0.0);
        report.setAverageExpense(report.getExpenseCount() > 0 ? report.getTotalExpense() / report.getExpenseCount() : 0.0);
        
        // Group by category
        Map<String, Double> incomeByCategory = incomes.stream()
            .collect(Collectors.groupingBy(
                Income::getCategory,
                Collectors.summingDouble(Income::getAmount)
            ));
        
        Map<String, Double> expenseByCategory = expenses.stream()
            .collect(Collectors.groupingBy(
                Expense::getCategory,
                Collectors.summingDouble(Expense::getAmount)
            ));
        
        report.setIncomeByCategory(incomeByCategory);
        report.setExpenseByCategory(expenseByCategory);
        report.setReportPeriod(startDate + " to " + endDate);
        
        return report;
    }
}
