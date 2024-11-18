package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class CalculatorOffersService {
    private final CreateLoanOfferService createOffer;

    @Autowired
    public CalculatorOffersService(CreateLoanOfferService createLoanOfferService) {
        this.createOffer = createLoanOfferService;
    }

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        BigDecimal amount = loanStatementRequestDto.getAmount();
        Integer term = loanStatementRequestDto.getTerm();

        List<LoanOfferDto> loanOfferDtos = new ArrayList<>();

        // offer без страховки, без зарплатного клиента
        LoanOfferDto loanOffer = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, false, false))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, false, false))
                .rate(createOffer.annualInterestRateCalculate(false, false))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
        loanOfferDtos.add(loanOffer);

        // offer + страховка, -  зарплатный клиент
        LoanOfferDto loanOfferWithInsurance = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, true, false))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, true, false))
                .rate(createOffer.annualInterestRateCalculate(true, false))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        loanOfferDtos.add(loanOfferWithInsurance);

        // offer  - страховка, +  зарплатный клиент
        LoanOfferDto loanOfferWithSalaryClient = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, false, true))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, false, true))
                .rate(createOffer.annualInterestRateCalculate(false, true))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        loanOfferDtos.add(loanOfferWithSalaryClient);

        // offer  + страховка, +  зарплатный клиент
        LoanOfferDto loanOfferWithInsuranceWithSalaryClient = LoanOfferDto.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, true, true))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, true, true))
                .rate(createOffer.annualInterestRateCalculate(true, true))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        loanOfferDtos.add(loanOfferWithInsuranceWithSalaryClient);

        // сортировка по итоговой ставке (от меньшей ставки к большей)
        loanOfferDtos.sort(Comparator.comparing(LoanOfferDto::getRate));

        log.info("loanOfferDtos - {}", loanOfferDtos);
        return loanOfferDtos;

    }
}
