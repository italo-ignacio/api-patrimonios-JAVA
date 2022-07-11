package com.api.manager.dtos;

import javax.validation.constraints.NotBlank;

public class PatrimonyDto {

    @NotBlank
    private String name;
    @NotBlank
    private String cod;
    @NotBlank
    private Long owner;

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

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
