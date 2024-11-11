package com.developer.onlybuns.entity;


import com.developer.onlybuns.enums.Uloga;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="registrovaniKorisnik")
public class RegistrovaniKorisnik extends Korisnik {

    @OneToMany(mappedBy = "registrovaniKorisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objava> objave;

    @OneToMany(mappedBy = "registrovaniKorisnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Komentar> komentari;

    public RegistrovaniKorisnik() {

    }

    public RegistrovaniKorisnik(List<Objava> objave, List<Komentar> komentari) {
        this.objave = objave;
        this.komentari = komentari;
    }

    public RegistrovaniKorisnik(Integer id, String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari) {
        super(id, korisnicko_ime, email, password, ime, prezime, ulica_broj, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
    }

    public RegistrovaniKorisnik(String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari) {
        super(korisnicko_ime, email, password, ime, prezime, ulica_broj, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
    }

}
