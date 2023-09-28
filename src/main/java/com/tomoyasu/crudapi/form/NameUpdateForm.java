package com.tomoyasu.crudapi.form;

public class NameUpdateForm {
    private String name;
    private String birth;

    public NameUpdateForm(String name, String birth) {
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
