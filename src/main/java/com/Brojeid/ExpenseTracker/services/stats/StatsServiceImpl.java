package com.Brojeid.ExpenseTracker.services.stats;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.GraphDTO;
import com.Brojeid.ExpenseTracker.dto.StatsDTO;
import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.repository.ExpenseRepository;
import com.Brojeid.ExpenseTracker.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    public GraphDTO getChartData(){
        LocalDate enDate = LocalDate.now();
        LocalDate starDate = enDate.minusDays(27);

        GraphDTO graphDTO = new GraphDTO();
        
        graphDTO.setExpensesList(expenseRepository.findByDateBetween(starDate, enDate));
        graphDTO.setIncomesList(incomeRepository.findByDateBetween(starDate, enDate));
         
        return graphDTO;

    }

    public StatsDTO getStats(){
         Double totalIncome = incomeRepository.sumAllAmounts();
         Double totalExpense = expenseRepository.sumAllAmounts();

         Optional<Income> optionalIncome = incomeRepository.findFirstByOrderByDateDesc();
         Optional<Expense> optionalExpense = expenseRepository.findFirstByOrderByDateDesc();

         StatsDTO statsDTO = new StatsDTO();
         statsDTO.setIncome(totalIncome);
         statsDTO.setExpense(totalExpense);

            optionalIncome.ifPresent(statsDTO::setLatestIncome);
            optionalExpense.ifPresent(statsDTO::setLatestExpense);
        
            return statsDTO; 
    }

}
