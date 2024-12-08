package com.neoflex.statement.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LatinateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Latinate {
    String message() default "ФИО - латинские буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}