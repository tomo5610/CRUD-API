package com.tomoyasu.crudapi.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthValidator implements ConstraintValidator<YearMonthValid, YearMonth> {
    private boolean allowEmpty;

    @Override
    public void initialize(YearMonthValid constraintAnnotation) {
        this.allowEmpty = constraintAnnotation.allowEmpty();
    }

    @Override
    public boolean isValid(YearMonth value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowEmpty;
        }

        try {
            YearMonth.now().with(value);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
