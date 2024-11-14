package com.neoflex.calculator.controller;

import com.neoflex.calculator.exeption.ScoringExeption;
import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.services.CalculatorService;
import com.neoflex.calculator.services.ScoringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final ScoringService scoringService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService, ScoringService scoringService) {
        this.calculatorService = calculatorService;
        this.scoringService = scoringService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculatorService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity calculateOffersCalc(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        CreditDto creditDto = new CreditDto();
        System.out.println(scoringDataDto);

        BigDecimal bigDecimal = scoringService.getFinalannualInterestRate(scoringDataDto);
        System.out.println("bigDecimal - " + bigDecimal);
        return ResponseEntity.ok(creditDto);
    }
}