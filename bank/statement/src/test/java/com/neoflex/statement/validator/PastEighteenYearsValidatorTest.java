package com.neoflex.statement.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PastEighteenYearsValidatorTest {

    private PastEighteenYearsValidator validator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new PastEighteenYearsValidator();
    }

    @Test
    public void testDateIsMoreThanEighteenYearsAgo() {
        LocalDate date = LocalDate.now().minusYears(19);
        assertTrue(validator.isValid(date, context));
    }

    @Test
    public void testDateIsExactlyEighteenYearsAgo() {
        LocalDate date = LocalDate.now().minusYears(18);
        assertFalse(validator.isValid(date, context));
    }

    @Test
    public void testDateIsLessThanEighteenYearsAgo() {
        LocalDate date = LocalDate.now().minusYears(17).minusDays(1);
        assertFalse(validator.isValid(date, context));
    }

    @Test
    public void testDateIsToday() {
        LocalDate date = LocalDate.now();
        assertFalse(validator.isValid(date, context));
    }
}