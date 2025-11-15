package com.Brojeid.ExpenseTracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Brojeid.ExpenseTracker.dto.GraphDTO;
import com.Brojeid.ExpenseTracker.dto.ReportDTO;
import com.Brojeid.ExpenseTracker.dto.ReportRequestDTO;
import com.Brojeid.ExpenseTracker.services.stats.StatsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

private final StatsService statsService;

@GetMapping("/chart")
public ResponseEntity<GraphDTO> getChartData(){
    return ResponseEntity.ok(statsService.getChartData());
}

@GetMapping
public ResponseEntity<?> getStats(){
    return ResponseEntity.ok(statsService.getStats());
}

@PostMapping("/reports")
public ResponseEntity<ReportDTO> getUserReport(@RequestBody ReportRequestDTO request){
    return ResponseEntity.ok(statsService.getUserReport(request.getStartDate(), request.getEndDate()));
}
}
