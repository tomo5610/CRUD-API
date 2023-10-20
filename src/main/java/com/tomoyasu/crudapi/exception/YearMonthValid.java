package com.tomoyasu.crudapi.exception;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearMonthValidator.class)
public @interface YearMonthValid {
    String message() default "Invalid YearMonth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
