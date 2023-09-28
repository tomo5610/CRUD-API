package com.tomoyasu.crudapi.form;

import com.tomoyasu.crudapi.exception.YearMonthValid;
import jakarta.validation.constraints.NotBlank;

import java.time.YearMonth;

public class NameCreateForm {
    @NotBlank
    private String name;
    
    @YearMonthValid(allowEmpty = false)
    private YearMonth birth;

    public NameCreateForm(String name, YearMonth birth) {
        this.name = name;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public YearMonth getBirth() {
        return birth;
    }
}
