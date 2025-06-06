package com.developer.onlybuns.dto.request;

public class LokacijaDTO {
    private String ulica;
    private String grad;
    private String drzava;

    public LokacijaDTO() {
    }

    public LokacijaDTO(String ulica, String grad, String drzava) {
        this.ulica = ulica;
        this.grad = grad;
        this.drzava = drzava;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }
}
