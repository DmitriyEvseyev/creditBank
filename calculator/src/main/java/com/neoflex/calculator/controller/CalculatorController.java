package com.neoflex.calculator.controller;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.services.CalculatorOffersService;
import com.neoflex.calculator.services.CreateCreditDtoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CalculatorController", description = "Кредитный калькулятор.")
public class CalculatorController {
    private final CalculatorOffersService calculatorOffersService;
    private final CreateCreditDtoService createCreditDtoService;

    @Autowired
    public CalculatorController(CalculatorOffersService calculatorOffersService, CreateCreditDtoService createCreditDtoService) {
        this.calculatorOffersService = calculatorOffersService;
        this.createCreditDtoService = createCreditDtoService;
    }

    @PostMapping("/offers")
    @Operation(summary = "Создаётся 4 кредитных предложения ",
            description = "создаётся 4 кредитных предложения LoanOfferDto " +
                    "на основании всех возможных комбинаций булевских полей isInsuranceEnabled и isSalaryClient ")
    public ResponseEntity<List<LoanOfferDto>> calculateOffers(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {
        List<LoanOfferDto> offers = calculatorOffersService.calculateOffers(loanStatementRequestDto);
        return ResponseEntity.ok(offers);
    }

    @PostMapping("/calc")
    @Operation(summary = "Расчет всех данных по кредиту.",
            description = "Происходит скоринг данных, высчитывание итоговой ставки(rate), " +
                    "полной стоимости кредита(psk), размер ежемесячного платежа(monthlyPayment), " +
                    "график ежемесячных платежей (List<PaymentScheduleElementDto>). ")
    public ResponseEntity<CreditDto> calculateOffersCalc(@RequestBody @Valid ScoringDataDto scoringDataDto) {
        CreditDto creditDto = createCreditDtoService.createCreditDto(scoringDataDto);
        return ResponseEntity.ok(creditDto);
    }
}