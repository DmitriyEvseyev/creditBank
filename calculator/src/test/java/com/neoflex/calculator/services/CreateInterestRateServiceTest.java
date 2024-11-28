package com.neoflex.calculator.services;

import com.neoflex.calculator.exeption.ScoringExeption;
import com.neoflex.calculator.model.dto.EmploymentDto;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.neoflex.calculator.model.enumDto.EmploymentStatusEnum.*;
import static com.neoflex.calculator.model.enumDto.GenderEnum.FEMALE;
import static com.neoflex.calculator.model.enumDto.GenderEnum.MALE;
import static com.neoflex.calculator.model.enumDto.MaritalStatusEnum.MARRIED;
import static com.neoflex.calculator.model.enumDto.MaritalStatusEnum.SINGLE;
import static com.neoflex.calculator.model.enumDto.PositionEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CreateInterestRateServiceTest {

    private CreateInterestRateService rateService;
    ScoringDataDto scoringDataDto;
    EmploymentDto employmentDto;

    @BeforeEach
    void setUp() {
        rateService = new CreateInterestRateService();

        rateService.setAnnualInterestRate(BigDecimal.valueOf(23));
        rateService.setSelfEmployedRate(BigDecimal.valueOf(-1));
        rateService.setBusinessOwnerRate(BigDecimal.valueOf(-2));

        rateService.setJuniorRate(BigDecimal.valueOf(-1));
        rateService.setMiddleRate(BigDecimal.valueOf(-2));
        rateService.setSeniorRate(BigDecimal.valueOf(-3));

        rateService.setMarriedRate(BigDecimal.valueOf(-1));
        rateService.setSingleRate(BigDecimal.valueOf(1));

        rateService.setMale30_55(BigDecimal.valueOf(-3));
        rateService.setFemale32_65(BigDecimal.valueOf(-3));

        employmentDto = EmploymentDto.builder()
                .employmentStatus(BUSINESS_OWNER)
                .employerINN("1234567890")
                .salary(BigDecimal.valueOf(150000))
                .position(MIDDLE)
                .workExperienceTotal(36)
                .workExperienceCurrent(20).build();
        scoringDataDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(200000))
                .term(24)
                .firstName("Ivan")
                .lastName("Ivanov")
                .middleName("Ivanovich")
                .gender(MALE)
                .birthdate(LocalDate.parse("1985-10-08"))
                .passportSeries("3607")
                .passportNumber("456123")
                .passportIssueDate(LocalDate.parse("2015-10-08"))
                .passportIssueBranch("Russia")
                .maritalStatus(MARRIED)
                .dependentAmount(1234567)
                .employment(employmentDto)
                .accountNumber("12345678901234567890")
                .isInsuranceEnabled(false)
                .isSalaryClient(true).build();
    }

    @Test
    void getFinalannualInterestRate_Success() {
        BigDecimal newRate = rateService.getFinalannualInterestRate(scoringDataDto);
        assertThat(newRate).isEqualTo(BigDecimal.valueOf(15));
    }

    @Test
    void getFinalannualInterestRate_InvalidSalary() {
        employmentDto.setSalary(BigDecimal.valueOf(100));
        assertAll(() -> {
            var ex = assertThrows(ScoringExeption.class, () -> rateService.getFinalannualInterestRate(scoringDataDto));
            assertThat(ex.getMessage()).isEqualTo("estimateSalary * 24 - false");
        });
    }

    @Test
    void getFinalannualInterestRate_InvalidAge() {
        scoringDataDto.setBirthdate(LocalDate.parse("2015-01-01"));
        assertAll(() -> {
                    var ex = assertThrows(ScoringExeption.class, () -> rateService.getFinalannualInterestRate(scoringDataDto));
                    assertThat(ex.getMessage()).isEqualTo("estimateAge - false");
                }
        );
    }

    @Test
    void getFinalannualInterestRate_InvalidWorkExperienceTotal() {
        employmentDto.setWorkExperienceTotal(10);
        assertAll(() -> {
                    var ex = assertThrows(ScoringExeption.class, () -> rateService.getFinalannualInterestRate(scoringDataDto));
                    assertThat(ex.getMessage()).isEqualTo("estimateWorkExperienceTotal - false");
                }
        );
    }

    @Test
    void getFinalannualInterestRate_InvalidWorkExperienceCurrent() {
        employmentDto.setWorkExperienceCurrent(1);
        assertAll(() -> {
                    ScoringExeption ex = assertThrows(ScoringExeption.class, () -> rateService.getFinalannualInterestRate(scoringDataDto));
                    assertThat(ex.getMessage()).isEqualTo("estimateWorkExperienceCurrent - false");
                }
        );
    }

    @Test
    void calculateRateEmploymentStatus_SuccessSelfEmployed() {
        BigDecimal rate = rateService.calculateRateEmploymentStatus(SELF_EMPLOYED);
        assertThat(rate).isEqualTo(rateService.getSelfEmployedRate());
    }

    @Test
    void calculateRateEmploymentStatus_SuccessBusinessOwnerRate() {
        BigDecimal rate = rateService.calculateRateEmploymentStatus(BUSINESS_OWNER);
        assertThat(rate).isEqualTo(rateService.getBusinessOwnerRate());
    }

    @Test
    void calculateRateEmploymentStatus_SuccessUnemployed() {
        assertAll(() -> {
            ScoringExeption ex = assertThrows(ScoringExeption.class, () -> rateService.calculateRateEmploymentStatus(UNEMPLOYED));
            assertThat(ex.getMessage()).isEqualTo("employmentStatus - UNEMPLOYED");
        });
    }

    @Test
    void calculateRatePosition_SuccessJunior() {
        BigDecimal rate = rateService.calculateRatePosition(JUNIOR);
        assertThat(rate).isEqualTo(rateService.getJuniorRate());
    }

    @Test
    void calculateRatePosition_SuccessMiddle() {
        BigDecimal rate = rateService.calculateRatePosition(MIDDLE);
        assertThat(rate).isEqualTo(rateService.getMiddleRate());
    }

    @Test
    void calculateRatePosition_SuccessSenior() {
        BigDecimal rate = rateService.calculateRatePosition(SENIOR);
        assertThat(rate).isEqualTo(rateService.getSeniorRate());
    }

    @Test
    void estimateSalary_Success() {
        BigDecimal amount = new BigDecimal("120000");
        BigDecimal salary = new BigDecimal("4000");
        Integer result = rateService.estimateSalary(amount, salary);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void estimateSalary_Failure() {
        BigDecimal amount = new BigDecimal("120000");
        BigDecimal salary = new BigDecimal("10000");
        Integer result = rateService.estimateSalary(amount, salary);
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void calculateRateMaritalStatus_Married() {
        BigDecimal rate = rateService.calculateRateMaritalStatus(MARRIED);
        assertThat(rate).isEqualTo(rateService.getMarriedRate());
    }

    @Test
    void calculateRateMaritalStatus_SuccessSingle() {
        BigDecimal rate = rateService.calculateRateMaritalStatus(SINGLE);
        assertThat(rate).isEqualTo(rateService.getSingleRate());
    }

    @Test
    void estimateAge_ValidAge() {
        LocalDate birthdate = LocalDate.of(2000, 1, 1); //24
        Boolean result = rateService.estimateAge(birthdate);
        assertThat(result).isEqualTo(true);
    }

    @Test
    void estimateAge_Underage() {
        LocalDate birthdate = LocalDate.of(2007, 1, 1); // 17
        Boolean result = rateService.estimateAge(birthdate);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void calculateRateGenderAndAge_Male_ValidAge() {
        LocalDate birthdate = LocalDate.of(1980, 1, 1); // 44 года
        BigDecimal rate = rateService.calculateRateGengerAngAge(MALE, birthdate);
        assertThat(rate).isEqualTo(rateService.getMale30_55());
    }

    @Test
    public void calculateRateGenderAndAge_Female_ValidAge() {
        LocalDate birthdate = LocalDate.of(1980, 1, 1); // 44 года
        BigDecimal rate = rateService.calculateRateGengerAngAge(FEMALE, birthdate);
        assertThat(rate).isEqualTo(rateService.getFemale32_65());
    }

    @Test
    public void estimateWorkExperienceTotal_Success() {
        Integer workExperienceTotal = 20;
        Boolean result = rateService.estimateWorkExperienceTotal(workExperienceTotal);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void estimateWorkExperienceTotal_Failure() {
        Integer workExperienceTotal = 12;
        Boolean result = rateService.estimateWorkExperienceTotal(workExperienceTotal);
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void estimateWorkExperienceCurrent_Success() {
        Integer workExperienceCurrent = 5;
        Boolean result = rateService.estimateWorkExperienceCurrent(workExperienceCurrent);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testEstimateWorkExperienceCurrent_Failure() {
        Integer workExperienceCurrent = 2;
        Boolean result = rateService.estimateWorkExperienceCurrent(workExperienceCurrent);
        assertThat(result).isEqualTo(false);
    }
}