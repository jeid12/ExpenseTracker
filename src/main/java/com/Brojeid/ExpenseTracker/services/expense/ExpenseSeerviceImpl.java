package com.Brojeid.ExpenseTracker.services.expense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.ExpenseDTO;
import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.repository.ExpenseRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseSeerviceImpl implements ExpenseService {
    
    private final ExpenseRepository expenseRepository;

    public Expense postExpense(ExpenseDTO expenseDTO){
        return saveOrUpdateExpense(new Expense(), expenseDTO);
    }

    private  Expense saveOrUpdateExpense(Expense expense, ExpenseDTO expenseDTO){
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDecription(expenseDTO.getDecription());
        
        return  expenseRepository.save(expense);
    }


    public Expense updateExpense(Long id, ExpenseDTO expenseDTO){
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        if(optionalexpense.isPresent()){
            return saveOrUpdateExpense(optionalexpense.get(), expenseDTO);
    }else{
        throw new EntityNotFoundException( "Expense is not present with id : " + id);
    }
    }
    public List<Expense> getAllExpenses(){
        return expenseRepository.findAll().stream().sorted(Comparator.comparing(Expense::getDate).reversed()).collect(Collectors.toList());
    }
    public Expense getExpenseById(Long id){
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        if(optionalexpense.isPresent()){
            return optionalexpense.get();
        }else{
            throw new EntityNotFoundException( "Expense is not present with id : " + id);
        }
    }
    public void deleteExpense(Long id){
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        if(optionalexpense.isPresent()){
            expenseRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException( "Expense is not present with id : " + id);
        }
    }
}