package com.tomoyasu.crudapi.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;
import java.time.format.DateTimeParseException;

public class YearMonthValidator implements ConstraintValidator<YearMonthValid, String> {

    @Override
    public void initialize(YearMonthValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            YearMonth.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
