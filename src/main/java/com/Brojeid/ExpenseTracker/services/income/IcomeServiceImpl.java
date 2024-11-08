package com.Brojeid.ExpenseTracker.services.income;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.IncomeDTO;
import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.repository.IncomeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IcomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    

    public Income postIncome(IncomeDTO incomeDTO){
        return saveOrUpdateIncome(new Income(), incomeDTO);
    }

    private Income saveOrUpdateIncome(Income income, IncomeDTO incomeDTO){
        income.setTitle(incomeDTO.getTitle());
        income.setDecription(incomeDTO.getDecription());
        income.setCategory(incomeDTO.getCategory());
        income.setDate(incomeDTO.getDate());
        income.setAmount(incomeDTO.getAmount());

        return incomeRepository.save(income);
    }
    public Income updatIncome(Long id, IncomeDTO incomeDTO){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return saveOrUpdateIncome(optionalIncome.get(), incomeDTO);
        }else{
            throw new EntityNotFoundException("Income is not present with id : " + id);
        }
    }
    
    public List<IncomeDTO> getAllIncomes(){
        return incomeRepository.findAll().stream().sorted(Comparator.comparing(Income::getDate).reversed()).map(Income::getIncomeDTO).collect(Collectors.toList());
    }

    public IncomeDTO getIncomeById(Long id){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return optionalIncome.get().getIncomeDTO();
        }else{
            throw new EntityNotFoundException("Income is not present with id : " + id);
        }
    }

}
