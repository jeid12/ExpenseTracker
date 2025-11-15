package com.Brojeid.ExpenseTracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Brojeid.ExpenseTracker.dto.IncomeDTO;
import com.Brojeid.ExpenseTracker.entity.Income;
import com.Brojeid.ExpenseTracker.services.income.IncomeService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

private final IncomeService incomeService;

@PostMapping
public ResponseEntity<?> postIncome(@RequestBody IncomeDTO incomeDTO){
    Income createdIncome = incomeService.postIncome(incomeDTO);
    if(createdIncome != null){
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIncome);
    }else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

@GetMapping("/all")
public ResponseEntity<?> getAllIncomes(){
    return ResponseEntity.ok(incomeService.getAllIncomes());
}

@PutMapping("/{id}")
public ResponseEntity<?> updateIncome(@PathVariable Long id, @RequestBody IncomeDTO incomeDTO){
    
    try {
        return ResponseEntity.ok(incomeService.updatIncome(id, incomeDTO));
    } catch(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

@GetMapping("/{id}")
public ResponseEntity<?> getIncomeById(@PathVariable Long id){
    try {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    } catch(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteIncome(@PathVariable Long id){
    try {
        incomeService.deleteIncome(id);
        return ResponseEntity.ok(null);
    } catch(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }   
}

}
