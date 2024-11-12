package com.neoflex.calculator.services;

import com.neoflex.calculator.model.LoanOffer;
import com.neoflex.calculator.model.LoanStatementRequest;
import com.neoflex.calculator.model.dto.LoanOfferDto;
import com.neoflex.calculator.model.dto.LoanStatementRequestDto;
import com.neoflex.calculator.utils.Converter;
import com.neoflex.calculator.utils.CreateLoanOffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CalculatorService {
    private final Converter converter;
    private final CreateLoanOffer createOffer;

    @Autowired
    public CalculatorService(Converter converter, CreateLoanOffer createLoanOffer) {
        this.converter = converter;
        this.createOffer = createLoanOffer;
    }

    public List<LoanOfferDto> calculateOffers(LoanStatementRequestDto loanStatementRequestDto) {
        LoanStatementRequest loanStatementRequest = converter
                .convertLoanStatementRequestDtoToLoanStatementRequest(loanStatementRequestDto);
        BigDecimal amount = loanStatementRequest.getAmount();
        Integer term = loanStatementRequest.getTerm();

        List<LoanOffer> offers = new ArrayList<>();

        // offer без страховки, без зарплатного клиента
        LoanOffer loanOffer = LoanOffer.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, false, false))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, false, false))
                .rate(createOffer.annualInterestRateCalculate(false, false))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
        offers.add(loanOffer);

        // offer + страховка, -  зарплатный клиент
        LoanOffer loanOfferWithInsurance = LoanOffer.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, true, false))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, true, false))
                .rate(createOffer.annualInterestRateCalculate(true, false))
                .isInsuranceEnabled(true)
                .isSalaryClient(false)
                .build();
        offers.add(loanOfferWithInsurance);

        // offer  - страховка, +  зарплатный клиент
        LoanOffer loanOfferWithSalaryClient = LoanOffer.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, false, true))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, false, true))
                .rate(createOffer.annualInterestRateCalculate(false, true))
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        offers.add(loanOfferWithSalaryClient);

        // offer  + страховка, +  зарплатный клиент
        LoanOffer loanOfferWithInsuranceWithSalaryClient = LoanOffer.builder()
                .uuid(createOffer.generateUUID())
                .requestedAmount(amount)
                .totalAmount(createOffer.getTotalAmount(amount, term, true, true))
                .term(term)
                .monthlyPayment(createOffer.getMonthlyPayment(amount, term, true, true))
                .rate(createOffer.annualInterestRateCalculate(true, true))
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        offers.add(loanOfferWithInsuranceWithSalaryClient);

        List<LoanOfferDto> loanOfferDtos = converter.convertListLoanOfferToListLoanOfferDto(offers);

        log.info("loanOfferDtos - {}", loanOfferDtos);
        return loanOfferDtos;

    }
}
