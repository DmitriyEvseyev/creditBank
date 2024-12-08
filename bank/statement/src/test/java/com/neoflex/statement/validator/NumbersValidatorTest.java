package com.neoflex.statement.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class NumbersValidatorTest {

    private NumbersValidator numbersValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        numbersValidator = new NumbersValidator();
    }

    @Test
    public void testValidNumberString() {
        String validNumber = "123456";
        assertTrue(numbersValidator.isValid(validNumber, context));
    }

    @Test
    public void testInvalidStringWithLetters() {
        String invalidString = "123abc";
        assertFalse(numbersValidator.isValid(invalidString, context));
    }

    @Test
    public void testInvalidStringWithSpecialCharacters() {
        String invalidString = "123?456";
        assertFalse(numbersValidator.isValid(invalidString, context));
    }

    @Test
    public void testEmptyString() {
        String emptyString = "";
        assertFalse(numbersValidator.isValid(emptyString, context),
                "The empty string should be considered invalid.");
    }
}