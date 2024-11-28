package com.neoflex.calculator.services;

import com.neoflex.calculator.model.dto.CreditDto;
import com.neoflex.calculator.model.dto.PaymentScheduleElementDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCreditDtoServiceTest {
    @InjectMocks
    private CreateCreditDtoService createCreditDtoService;
    @Mock
    private CreateInterestRateService createInterestRateService;
    @Mock
    private CreateMonthlyPaymentService createMonthlyPaymentService;
    @Mock
    private CreatePaymentScheduleElementDtoService createPaymentScheduleElementDtoService;

    private BigDecimal insurance = BigDecimal.valueOf(1000);

    @BeforeEach
    public void setUp() {
        createCreditDtoService.setInsurance(insurance);
    }

    @Test
    public void CreateCreditDto_WithInsurance() {
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(50000));
        scoringDataDto.setTerm(12);
        scoringDataDto.setIsInsuranceEnabled(true);
        scoringDataDto.setIsSalaryClient(false);

        BigDecimal rate = BigDecimal.valueOf(15);
        BigDecimal monthlyPayment = BigDecimal.valueOf(5000);

        PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto();
        paymentScheduleElementDto.setNumber(12);
        paymentScheduleElementDto.setTotalPayment(monthlyPayment);
        paymentScheduleElementDto.setInterestPayment(BigDecimal.ZERO);

        when(createInterestRateService.getFinalannualInterestRate(scoringDataDto)).thenReturn(rate);
        when(createMonthlyPaymentService.getMonthlyPayment(scoringDataDto.getAmount(), rate, scoringDataDto.getTerm()))
                .thenReturn(monthlyPayment);
        when(createPaymentScheduleElementDtoService.createPaymentScheduleElementDto(scoringDataDto, rate, monthlyPayment))
                .thenReturn(Collections.singletonList(paymentScheduleElementDto));

        // Act
        CreditDto creditDto = createCreditDtoService.createCreditDto(scoringDataDto);

        // Assert
        assertNotNull(creditDto);
        assertEquals(scoringDataDto.getAmount(), creditDto.getAmount());
        assertEquals(scoringDataDto.getTerm(), creditDto.getTerm());
        assertEquals(monthlyPayment, creditDto.getMonthlyPayment());
        assertEquals(rate, creditDto.getRate());
        assertEquals(monthlyPayment
                        .multiply(new BigDecimal(11))
                        .add(paymentScheduleElementDto.getTotalPayment())
                        .add(paymentScheduleElementDto.getInterestPayment())
                        .add(insurance)
                        .subtract(scoringDataDto.getAmount())
                , creditDto.getPsk());
        assertTrue(creditDto.getIsInsuranceEnabled());
    }

    @Test
    public void CreateCreditDto_WithoutInsurance() {
        // Arrange
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        scoringDataDto.setAmount(BigDecimal.valueOf(50000));
        scoringDataDto.setTerm(12);
        scoringDataDto.setIsInsuranceEnabled(false);
        scoringDataDto.setIsSalaryClient(false);

        BigDecimal rate = BigDecimal.valueOf(5);
        BigDecimal monthlyPayment = BigDecimal.valueOf(5000);

        PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto();
        paymentScheduleElementDto.setNumber(12);
        paymentScheduleElementDto.setTotalPayment(monthlyPayment);
        paymentScheduleElementDto.setInterestPayment(BigDecimal.ZERO);

        when(createInterestRateService.getFinalannualInterestRate(scoringDataDto)).thenReturn(rate);
        when(createMonthlyPaymentService.getMonthlyPayment(scoringDataDto.getAmount(), rate, scoringDataDto.getTerm()))
                .thenReturn(monthlyPayment);
        when(createPaymentScheduleElementDtoService.createPaymentScheduleElementDto(scoringDataDto, rate, monthlyPayment))
                .thenReturn(Collections.singletonList(paymentScheduleElementDto));

        // Act
        CreditDto creditDto = createCreditDtoService.createCreditDto(scoringDataDto);

        // Assert
        assertNotNull(creditDto);
        assertEquals(scoringDataDto.getAmount(), creditDto.getAmount());
        assertEquals(scoringDataDto.getTerm(), creditDto.getTerm());
        assertEquals(monthlyPayment, creditDto.getMonthlyPayment());
        assertEquals(rate, creditDto.getRate());
        assertEquals(monthlyPayment.multiply(new BigDecimal(11))
                        .add(paymentScheduleElementDto.getTotalPayment())
                        .add(paymentScheduleElementDto.getInterestPayment())
                        .subtract(scoringDataDto.getAmount())
                , creditDto.getPsk());
        assertFalse(creditDto.getIsInsuranceEnabled());
    }


//    @Mock
//    private CreateInterestRateService createInterestRateService;
//
//    @Mock
//    private CreateMonthlyPaymentService createMonthlyPaymentService;
//
//    @Mock
//    private CreatePaymentScheduleElementDtoService createPaymentScheduleElementDtoService;
//
//    @InjectMocks
//    private CreateCreditDtoService service;
//
//    private ScoringDataDto scoringDataDto;
//    private EmploymentDto employmentDto;
//
//    @BeforeEach
//    void setUp() {
//        createInterestRateService.setAnnualInterestRate(BigDecimal.valueOf(23));
//        createInterestRateService.setSelfEmployedRate(BigDecimal.valueOf(-1));
//        createInterestRateService.setBusinessOwnerRate(BigDecimal.valueOf(-2));
//
//        createInterestRateService.setJuniorRate(BigDecimal.valueOf(-1));
//        createInterestRateService.setMiddleRate(BigDecimal.valueOf(-2));
//        createInterestRateService.setSeniorRate(BigDecimal.valueOf(-3));
//
//        createInterestRateService.setMarriedRate(BigDecimal.valueOf(-1));
//        createInterestRateService.setSingleRate(BigDecimal.valueOf(1));
//
//        createInterestRateService.setMale30_55(BigDecimal.valueOf(-3));
//        createInterestRateService.setFemale32_65(BigDecimal.valueOf(-3));
//
//        employmentDto = EmploymentDto.builder()
//                .employmentStatus(EmploymentStatusEnum.BUSINESS_OWNER)
//                .employerINN("1234567890")
//                .salary(new BigDecimal("100000"))
//                .position(PositionEnum.MIDDLE)
//                .workExperienceTotal(48)
//                .workExperienceCurrent(24).build();
//        scoringDataDto = new ScoringDataDto();
//        scoringDataDto.setAmount(new BigDecimal("150000"));
//        scoringDataDto.setTerm(36);
//        scoringDataDto.setBirthdate(LocalDate.parse("1985-03-03"));
//        scoringDataDto.setMaritalStatus(MaritalStatusEnum.MARRIED);
//        scoringDataDto.setEmployment(employmentDto);
//        scoringDataDto.setIsInsuranceEnabled(false);
//        scoringDataDto.setIsSalaryClient(true);
//
//
//
//
//    }
//
//    @Test
//    void createCreditDto() {
//        when(createInterestRateService.getFinalannualInterestRate(scoringDataDto))
//                .thenReturn(new BigDecimal("15"));
//
//        when(createPaymentScheduleElementDtoService.createPaymentScheduleElementDto(any(), any(), any()))
//                .thenReturn(new ArrayList<>());
//
//        when(createMonthlyPaymentService.getMonthlyPayment(new BigDecimal("150000"),
//                new BigDecimal("15"), 36))
//                .thenReturn(new BigDecimal("5574.84"));
//
//
//
//
//        CreditDto creditDto = service.createCreditDto(scoringDataDto);
//
//        assertNull(creditDto);
//
//    }
}