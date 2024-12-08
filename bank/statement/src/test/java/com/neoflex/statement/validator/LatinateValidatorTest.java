package com.neoflex.statement.validator;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class LatinateValidatorTest {

    private LatinateValidator latinateValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        latinateValidator = new LatinateValidator();
    }

    @Test
    public void testValidLatinateString() {
        String validString = "Ivanov";
        assertTrue(latinateValidator.isValid(validString, context));
    }

    @Test
    public void testInvalidLatinateStringWithNumbers() {
        String invalidString = "Ivan123";
        assertFalse(latinateValidator.isValid(invalidString, context));
    }

    @Test
    public void testInvalidLatinateStringWithSpecialCharacters() {
        String invalidString = "Иванов";
        assertFalse(latinateValidator.isValid(invalidString, context));
    }

    @Test
    public void testEmptyString() {
        String emptyString = "";
        assertFalse(latinateValidator.isValid(emptyString, context));
    }
}