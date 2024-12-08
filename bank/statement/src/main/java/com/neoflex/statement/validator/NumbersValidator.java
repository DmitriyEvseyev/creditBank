package com.neoflex.statement.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumbersValidator implements ConstraintValidator<Numbers, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext context) {
        String regex = "^[0-9]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }
}