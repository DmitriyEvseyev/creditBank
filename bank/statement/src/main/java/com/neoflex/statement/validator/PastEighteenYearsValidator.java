package com.neoflex.statement.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class PastEighteenYearsValidator implements ConstraintValidator<PastEighteenYears, LocalDate> {

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        LocalDate eighteenYearsAgo = now.minus(Period.ofYears(18));
        return date.isBefore(eighteenYearsAgo);
    }
}