package com.Brojeid.ExpenseTracker.services.income;



import java.util.List;

import com.Brojeid.ExpenseTracker.dto.IncomeDTO;
import com.Brojeid.ExpenseTracker.entity.Income;


public interface IncomeService {

    Income postIncome(IncomeDTO incomeDTO);

    List<IncomeDTO> getAllIncomes();

    Income updatIncome(Long id, IncomeDTO incomeDTO);

    IncomeDTO getIncomeById(Long id);

    void deleteIncome(Long id);
}
