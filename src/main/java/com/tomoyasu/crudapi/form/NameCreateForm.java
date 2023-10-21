package com.tomoyasu.crudapi.form;

import com.tomoyasu.crudapi.exception.YearMonthValid;
import jakarta.validation.constraints.NotBlank;

public class NameCreateForm {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @YearMonthValid(message = "Invalid YearMonth format")
    private String birth;

    public NameCreateForm(String name, String birth) {
        this.name = name;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }
}
