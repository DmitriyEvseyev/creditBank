package com.neoflex.calculator.services;

import com.neoflex.calculator.exeption.ScoringExeption;
import com.neoflex.calculator.model.ScoringData;
import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.model.enam.EmploymentStatusEnam;
import com.neoflex.calculator.model.enam.GenderEnam;
import com.neoflex.calculator.model.enam.MaritalStatusEnam;
import com.neoflex.calculator.model.enam.PositionEnam;
import com.neoflex.calculator.utils.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class ScoringService {
    @Value("${application.bank.interestrate}")
    private BigDecimal annualInterestRate;

    @Value("${application.bank.employmentStatus.SELF_EMPLOYED}")
    private BigDecimal selfEmployedRate;

    @Value("${application.bank.employmentStatus.BUSINESS_OWNER}")
    private BigDecimal businessOwnerRate;

    @Value("${application.bank.position.JUNIOR}")
    private BigDecimal juniorRate;

    @Value("${application.bank.position.MIDDLE}")
    private BigDecimal middleRate;

    @Value("${application.bank.position.SENIOR}")
    private BigDecimal seniorRate;

    @Value("${application.bank.maritalStatus.MARRIED}")
    private BigDecimal marriedRate;

    @Value("${application.bank.maritalStatus.SINGLE}")
    private BigDecimal singleRate;

    @Value("${application.bank.genderAndAgeRate.male30_55}")
    private BigDecimal male30_55;

    @Value("${application.bank.genderAndAgeRate.female32_65}")
    private BigDecimal female32_65;

    private final Converter converter;

    @Autowired
    public ScoringService(Converter converter) {
        this.converter = converter;
    }

    public BigDecimal getFinalannualInterestRate(ScoringDataDto scoringDataDto) throws ScoringExeption {
        ScoringData scoringData = converter.convertScoringDataDtoToScoringData(scoringDataDto);

        if (estimateSalary(scoringData.getAmount(), scoringData.getEmployment().getSalary()) == 1)
            throw new ScoringExeption("estimateSalary * 24 - false");
        if (!estimateAge(scoringData.getBirthdate()))
            throw new ScoringExeption("estimateAge - false");
        if (!estimateWorkExperienceTotal(scoringData.getEmployment().getWorkExperienceTotal()))
            throw new ScoringExeption("estimateWorkExperienceTotal - false");
        if (!estimateWorkExperienceCurrent(scoringData.getEmployment().getWorkExperienceCurrent()))
            throw new ScoringExeption("estimateWorkExperienceCurrent - false");

        // корретировка % ставки
        BigDecimal correctRate = calculateRateEmploymentStatus(scoringData.getEmployment().getEmploymentStatus())
                .add(calculateRatePosition(scoringData.getEmployment().getPosition()))
                .add(calculateRateMaritalStatus(scoringData.getMaritalStatus()))
                .add(calculateRateGengerAngAge(scoringData.getGender(), scoringData.getBirthdate()));

        log.info("correctRate - {}", correctRate);
        return annualInterestRate.subtract(correctRate);
    }


    //расчет % в зависимости от статуса работотника
    public BigDecimal calculateRateEmploymentStatus(EmploymentStatusEnam employmentStatusEnam) throws ScoringExeption {
//        if (employmentStatusEnam.equals(EmploymentStatusEnam.UNEMPLOYED))
//            throw new ScoringExeption("employmentStatus - UNEMPLOYED");
//        if (employmentStatusEnam.equals(EmploymentStatusEnam.SELF_EMPLOYED))
//            return  annualInterestRate.subtract(selfEmployedRate);
        BigDecimal calculateRateEmploymentStatus = new BigDecimal(0);
        switch (employmentStatusEnam) {
            case UNEMPLOYED -> throw new ScoringExeption("employmentStatus - UNEMPLOYED");
            case SELF_EMPLOYED -> {
                return calculateRateEmploymentStatus.subtract(selfEmployedRate);
            }
            case BUSINESS_OWNER -> {
                return calculateRateEmploymentStatus.subtract(businessOwnerRate);
            }
        }
        log.info("calculateRateEmploymentStatus - {}", calculateRateEmploymentStatus);
        return calculateRateEmploymentStatus;
    }

    //расчет  % в зависимости от должности
    public BigDecimal calculateRatePosition(PositionEnam positionEnam) {
        BigDecimal calculateRatePosition = new BigDecimal(0);
        switch (positionEnam) {
            case JUNIOR -> {
                return calculateRatePosition.subtract(juniorRate);
            }
            case MIDDLE -> {
                return calculateRatePosition.subtract(middleRate);
            }
            case SENIOR -> {
                return calculateRatePosition.subtract(seniorRate);
            }
        }
        log.info("calculateRatePosition - {}", calculateRatePosition);
        return calculateRatePosition;
    }

    //оценка зарплаты * 24 (1 - amount больше)
    public Integer estimateSalary(BigDecimal amount, BigDecimal salary) {
        return amount.compareTo(salary.multiply(new BigDecimal(24)));
    }

    // расчет % в зависимости семейного положения
    public BigDecimal calculateRateMaritalStatus(MaritalStatusEnam maritalStatusEnam) {
        BigDecimal calculateRateMaritalStatus = new BigDecimal(0);
        switch (maritalStatusEnam) {
            case MARRIED -> {
                return calculateRateMaritalStatus.subtract(marriedRate);
            }
            case SINGLE -> {
                return calculateRateMaritalStatus.subtract(singleRate);
            }
        }
        log.info("calculateRateMaritalStatus - {}", calculateRateMaritalStatus);
        return calculateRateMaritalStatus;
    }

    //оценка зарплаты от возраста. Возраст менее 20 или более 65 лет → отказ
    public Boolean estimateAge(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        return age > 20 & age < 65;
    }

    // расчет % в зависимости семейного положения
    public BigDecimal calculateRateGengerAngAge(GenderEnam genderEnam, LocalDate birthdate) {
        BigDecimal calculateRateGengerAngAge = new BigDecimal(0);
        switch (genderEnam) {
            case MALE -> {
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                if (age > 30 & age < 55)
                    return calculateRateGengerAngAge.subtract(male30_55);
            }
            case FEMALE -> {
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                if (age > 30 & age < 55)
                    return calculateRateGengerAngAge.subtract(female32_65);
            }
        }
        log.info("calculateRateGengerAngAge - {}", calculateRateGengerAngAge);
        return calculateRateGengerAngAge;
    }

    //оценка -  cтаж работы: Общий стаж менее 18 месяцев → отказ
    public Boolean estimateWorkExperienceTotal(Integer workExperienceTotal) {
        return workExperienceTotal >= 18;
    }

    //оценка -  Текущий стаж менее 3 месяцев → отказ
    public Boolean estimateWorkExperienceCurrent(Integer workExperienceCurrent) {
        return workExperienceCurrent >= 3;
    }
}
