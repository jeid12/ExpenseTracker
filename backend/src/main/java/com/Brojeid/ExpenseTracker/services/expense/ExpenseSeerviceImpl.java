package com.Brojeid.ExpenseTracker.services.expense;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.ExpenseDTO;
import com.Brojeid.ExpenseTracker.entity.Expense;
import com.Brojeid.ExpenseTracker.entity.User;
import com.Brojeid.ExpenseTracker.repository.ExpenseRepository;
import com.Brojeid.ExpenseTracker.services.user.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseSeerviceImpl implements ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    
    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserEntityByEmail(email);
    }

    public Expense postExpense(ExpenseDTO expenseDTO){
        User user = getCurrentUser();
        Expense expense = new Expense();
        expense.setUser(user);
        return saveOrUpdateExpense(expense, expenseDTO);
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
        User currentUser = getCurrentUser();
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        
        if(optionalexpense.isPresent()){
            Expense expense = optionalexpense.get();
            // Verify the expense belongs to the current user
            if (!expense.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only update your own expenses");
            }
            return saveOrUpdateExpense(expense, expenseDTO);
        }else{
            throw new EntityNotFoundException( "Expense is not present with id : " + id);
        }
    }
    
    public List<Expense> getAllExpenses(){
        User currentUser = getCurrentUser();
        return expenseRepository.findByUserOrderByDateDesc(currentUser);
    }
    
    public Expense getExpenseById(Long id){
        User currentUser = getCurrentUser();
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        
        if(optionalexpense.isPresent()){
            Expense expense = optionalexpense.get();
            // Verify the expense belongs to the current user
            if (!expense.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only access your own expenses");
            }
            return expense;
        }else{
            throw new EntityNotFoundException( "Expense is not present with id : " + id);
        }
    }
    
    public void deleteExpense(Long id){
        User currentUser = getCurrentUser();
        Optional<Expense> optionalexpense = expenseRepository.findById(id);
        
        if(optionalexpense.isPresent()){
            Expense expense = optionalexpense.get();
            // Verify the expense belongs to the current user
            if (!expense.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only delete your own expenses");
            }
            expenseRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException( "Expense is not present with id : " + id);
        }
    }
}
