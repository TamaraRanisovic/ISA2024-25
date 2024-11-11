package com.developer.onlybuns.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public class NovaObjavaDTO {

    private String opis;

    private String slika;

    private Double g_sirina;

    private Double g_duzina;

    private LocalDateTime datum_objave;

    private String korisnicko_ime;


    public NovaObjavaDTO() {
    }

    public NovaObjavaDTO(String opis, String slika, Double g_sirina, Double g_duzina, LocalDateTime datum_objave, String korisnicko_ime) {
        this.opis = opis;
        this.slika = slika;
        this.g_sirina = g_sirina;
        this.g_duzina = g_duzina;
        this.datum_objave = datum_objave;
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    public Double getG_sirina() {
        return g_sirina;
    }

    public void setG_sirina(Double g_sirina) {
        this.g_sirina = g_sirina;
    }

    public Double getG_duzina() {
        return g_duzina;
    }

    public void setG_duzina(Double g_duzina) {
        this.g_duzina = g_duzina;
    }

    public LocalDateTime getDatum_objave() {
        return datum_objave;
    }

    public void setDatum_objave(LocalDateTime datum_objave) {
        this.datum_objave = datum_objave;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }
}
