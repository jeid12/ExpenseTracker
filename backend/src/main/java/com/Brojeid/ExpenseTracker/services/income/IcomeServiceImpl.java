package com.Brojeid.ExpenseTracker.services.income;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Brojeid.ExpenseTracker.dto.IncomeDTO;
import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.entity.User;
import com.Brojeid.ExpenseTracker.repository.IncomeRepository;
import com.Brojeid.ExpenseTracker.services.user.UserService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IcomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    
    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getUserEntityByEmail(email);
    }

    public Income postIncome(IncomeDTO incomeDTO){
        User user = getCurrentUser();
        Income income = new Income();
        income.setUser(user);
        return saveOrUpdateIncome(income, incomeDTO);
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
        User currentUser = getCurrentUser();
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        
        if(optionalIncome.isPresent()){
            Income income = optionalIncome.get();
            // Verify the income belongs to the current user
            if (!income.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only update your own income");
            }
            return saveOrUpdateIncome(income, incomeDTO);
        }else{
            throw new EntityNotFoundException("Income is not present with id : " + id);
        }
    }
    
    public List<IncomeDTO> getAllIncomes(){
        User currentUser = getCurrentUser();
        return incomeRepository.findByUserOrderByDateDesc(currentUser)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public IncomeDTO getIncomeById(Long id){
        User currentUser = getCurrentUser();
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        
        if(optionalIncome.isPresent()){
            Income income = optionalIncome.get();
            // Verify the income belongs to the current user
            if (!income.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only access your own income");
            }
            return convertToDTO(income);
        }else{
            throw new EntityNotFoundException("Income is not present with id : " + id);
        }
    }

    public void deleteIncome(Long id){
        User currentUser = getCurrentUser();
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        
        if(optionalIncome.isPresent()){
            Income income = optionalIncome.get();
            // Verify the income belongs to the current user
            if (!income.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("Unauthorized: You can only delete your own income");
            }
            incomeRepository.delete(income);
        }else{
            throw new EntityNotFoundException("Income is not present with id : " + id);
        }
    }
    
    private IncomeDTO convertToDTO(Income income) {
        IncomeDTO dto = new IncomeDTO();
        dto.setId(income.getId());
        dto.setTitle(income.getTitle());
        dto.setAmount(income.getAmount());
        dto.setDate(income.getDate());
        dto.setCategory(income.getCategory());
        dto.setDecription(income.getDecription());
        return dto;
    }
}
