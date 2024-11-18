package com.neoflex.calculator.services;

import com.neoflex.calculator.exeption.ScoringExeption;

import com.neoflex.calculator.model.dto.ScoringDataDto;
import com.neoflex.calculator.model.enumDto.EmploymentStatusEnum;
import com.neoflex.calculator.model.enumDto.GenderEnum;
import com.neoflex.calculator.model.enumDto.MaritalStatusEnum;
import com.neoflex.calculator.model.enumDto.PositionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@Slf4j
public class CreateInterestRateService {
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

    public BigDecimal getFinalannualInterestRate(ScoringDataDto scoringDataDto) {

        if (estimateSalary(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary()) == 1)
            throw new ScoringExeption("estimateSalary * 24 - false");
        if (!estimateAge(scoringDataDto.getBirthdate()))
            throw new ScoringExeption("estimateAge - false");
        if (!estimateWorkExperienceTotal(scoringDataDto.getEmployment().getWorkExperienceTotal()))
            throw new ScoringExeption("estimateWorkExperienceTotal - false");
        if (!estimateWorkExperienceCurrent(scoringDataDto.getEmployment().getWorkExperienceCurrent()))
            throw new ScoringExeption("estimateWorkExperienceCurrent - false");

        // корретировка % ставки
        BigDecimal correctRate = calculateRateEmploymentStatus(scoringDataDto.getEmployment().getEmploymentStatus())
                .add(calculateRatePosition(scoringDataDto.getEmployment().getPosition()))
                .add(calculateRateMaritalStatus(scoringDataDto.getMaritalStatus()))
                .add(calculateRateGengerAngAge(scoringDataDto.getGender(), scoringDataDto.getBirthdate()));

        log.info("correctRate - {}", correctRate);
        return annualInterestRate.add(correctRate);
    }


    //расчет % в зависимости от статуса работотника
    public BigDecimal calculateRateEmploymentStatus(EmploymentStatusEnum employmentStatusEnum) {
        switch (employmentStatusEnum) {
            case UNEMPLOYED -> throw new ScoringExeption("employmentStatus - UNEMPLOYED");
            case SELF_EMPLOYED -> {
                log.info("calculateRateEmploymentStatus/ SELF_EMPLOYED: {}", selfEmployedRate);
                return selfEmployedRate;
            }
            case BUSINESS_OWNER -> {
                log.info("calculateRateEmploymentStatus/ BUSINESS_OWNER: {}", businessOwnerRate);
                return businessOwnerRate;
            }
            default -> {
                log.warn("Unknown EmploymentStatus: {}", employmentStatusEnum);
                return BigDecimal.ZERO;
            }
        }
    }

    //расчет  % в зависимости от должности
    public BigDecimal calculateRatePosition(PositionEnum positionEnum) {
        switch (positionEnum) {
            case JUNIOR -> {
                log.info("calculateRatePosition/ JUNIOR: {}", juniorRate);
                return juniorRate;
            }
            case MIDDLE -> {
                log.info("calculateRatePosition/ MIDDLE: {}", middleRate);
                return middleRate;
            }
            case SENIOR -> {
                log.info("calculateRatePosition/ SENIOR: {}", seniorRate);
                return seniorRate;
            }
            default -> {
                log.warn("Unknown position: {}", positionEnum);
                return BigDecimal.ZERO;
            }
        }
    }

    //оценка зарплаты * 24 (1 - amount больше)
    public Integer estimateSalary(BigDecimal amount, BigDecimal salary) {
        Integer estimateSalary = amount.compareTo(salary.multiply(new BigDecimal(24)));
        log.info("estimateSalary - {}", estimateSalary);
        return estimateSalary;
    }

    // расчет % в зависимости семейного положения
    public BigDecimal calculateRateMaritalStatus(MaritalStatusEnum maritalStatusEnum) {
        switch (maritalStatusEnum) {
            case MARRIED -> {
                log.info("calculateRateMaritalStatus/ MARRIED: {}", marriedRate);
                return marriedRate;
            }
            case SINGLE -> {
                log.info("calculateRateMaritalStatus/ SINGLE: {}", singleRate);
                return singleRate;
            }
            default -> {
                log.warn("Unknown MaritalStatus: {}", maritalStatusEnum);
                return BigDecimal.ZERO;
            }
        }
    }

    //оценка зарплаты от возраста. Возраст менее 20 или более 65 лет → отказ
    public Boolean estimateAge(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        Boolean estimateAge = age > 20 & age < 65;
        log.info("estimateAge - {}", estimateAge);
        return estimateAge;
    }

    // расчет % в зависимости семейного положения
    public BigDecimal calculateRateGengerAngAge(GenderEnum genderEnum, LocalDate birthdate) {
        switch (genderEnum) {
            case MALE -> {
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                if (age > 30 & age < 55) {
                    log.info("calculateRateGengerAngAge/ MALE: {}", male30_55);
                    return male30_55;
                }
            }
            case FEMALE -> {
                int age = Period.between(birthdate, LocalDate.now()).getYears();
                if (age > 30 & age < 55) {
                    log.info("calculateRateGengerAngAge/ FEMALE: {}", female32_65);
                    return female32_65;
                }
            }
            default -> {
                log.warn("Unknown genderEnam: {}", genderEnum);
                return BigDecimal.ZERO;
            }
        }
        log.info("calculateRateGenderAndAge - No applicable rate");
        return BigDecimal.ZERO;
    }

    //оценка -  cтаж работы: Общий стаж менее 18 месяцев → отказ
    public Boolean estimateWorkExperienceTotal(Integer workExperienceTotal) {
        Boolean estimateWorkExperienceTotal = workExperienceTotal >= 18;
        log.info("estimateWorkExperienceTotal - {}", estimateWorkExperienceTotal);
        return estimateWorkExperienceTotal;
    }

    //оценка -  Текущий стаж менее 3 месяцев → отказ
    public Boolean estimateWorkExperienceCurrent(Integer workExperienceCurrent) {
        Boolean estimateWorkExperienceCurrent = workExperienceCurrent >= 3;
        log.info("estimateWorkExperienceCurrent - {}", estimateWorkExperienceCurrent);
        return estimateWorkExperienceCurrent;
    }
}
