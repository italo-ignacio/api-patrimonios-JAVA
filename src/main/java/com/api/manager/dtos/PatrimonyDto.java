package com.api.manager.dtos;

import com.api.manager.models.UserModel;

import javax.validation.constraints.NotBlank;

public class PatrimonyDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cod;

    private UserModel user;

    private String note;
    private String details;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
