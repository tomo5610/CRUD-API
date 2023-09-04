package com.tomoyasu.crudapi.form;


public class NameCreateForm {
    private String name;
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
