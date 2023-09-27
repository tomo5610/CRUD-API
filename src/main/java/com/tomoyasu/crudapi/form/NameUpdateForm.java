package com.tomoyasu.crudapi.form;

import java.time.YearMonth;

public class NameUpdateForm {
    private String name;
    private YearMonth birth;

    public NameUpdateForm(String name, YearMonth birth) {
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
