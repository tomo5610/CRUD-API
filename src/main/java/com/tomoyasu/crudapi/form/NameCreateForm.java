package com.tomoyasu.crudapi.form;

import jakarta.validation.constraints.NotBlank;

public class NameCreateForm {
    @NotBlank
    private String name;
    @NotBlank
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
