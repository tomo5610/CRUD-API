package com.tomoyasu.crudapi.exception;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        assertTrue(validator.isValid("2023-10", context));
    }

    @Test
    public void testInvalidYearMonth() {
        assertFalse(validator.isValid("2023-13", context));
    }

    @Test
    public void testEmptyString() {
        assertFalse(validator.isValid("", context));
    }
}
