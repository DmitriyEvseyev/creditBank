package com.neoflex.calculator.controller;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.services.CalculatorOffersService;
import com.neoflex.calculator.services.CreateCreditDtoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@Slf4j
public class CalculatorController {
    private final CalculatorOffersService calculatorOffersService;
    private final CreateCreditDtoService createCreditDtoService;

    @Autowired
    public CalculatorController(CalculatorOffersService calculatorOffersService, CreateCreditDtoService createCreditDtoService) {
        this.calculatorOffersService = calculatorOffersService;
        this.createCreditDtoService = createCreditDtoService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculatorOffersService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    public ResponseEntity<CreditDto> calculateOffersCalc(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        CreditDto creditDto = createCreditDtoService.createCreditDto(scoringDataDto);


        return ResponseEntity.ok(creditDto);
    }
}