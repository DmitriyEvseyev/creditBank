package com.neoflex.calculator.controller;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculatorService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateOffersCalc(@RequestBody ScoringDataDto scoringDataDto) {
        CreditDto creditDto = new CreditDto();


        return ResponseEntity.ok(creditDto);
    }
}