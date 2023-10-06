package com.tomoyasu.crudapi.exception;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YearMonthValidatorTest {

    private YearMonthValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new YearMonthValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testValidYearMonth() {
        validator.initialize(createYearMonthValidAnnotation(false));

        assertTrue(validator.isValid(YearMonth.of(2023, 9), context));
    }

    @Test
    public void testInvalidYearMonth() {
        validator.initialize(createYearMonthValidAnnotation(false));

        assertFalse(validator.isValid(YearMonth.of(2023, 13), context));
    }

    @Test
    public void testAllowEmpty() {
        assertFalse(validator.isValid(null, context));

        validator.initialize(createYearMonthValidAnnotation(true));
        assertTrue(validator.isValid(null, context));
    }

    private YearMonthValid createYearMonthValidAnnotation(boolean allowEmpty) {
        return new YearMonthValid() {
            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public String message() {
                return "Invalid YearMonth";
            }

            @Override
            public boolean allowEmpty() {
                return allowEmpty;
            }

            @Override
            public Class<? extends jakarta.validation.Constraint> annotationType() {
                return jakarta.validation.Constraint.class;
            }
        };
    }
}

