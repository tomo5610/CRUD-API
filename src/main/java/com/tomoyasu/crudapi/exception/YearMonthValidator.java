package com.tomoyasu.crudapi.exception;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthValidator implements ConstraintValidator<YearMonthValid, YearMonth> {
    private boolean allowEmpty; // allowEmpty フラグを保持

    @Override
    public void initialize(YearMonthValid constraintAnnotation) {
        // 初期化コード（通常は何もしない）
        this.allowEmpty = constraintAnnotation.allowEmpty(); // allowEmpty フラグを設定
    }

    @Override
    public boolean isValid(YearMonth value, ConstraintValidatorContext context) {
        if (value == null) {
            return allowEmpty; // フィールドが null の場合、allowEmpty フラグに基づいて判断
        }

        try {
            // 有効な年月であることを確認
            YearMonth.now().with(value);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
