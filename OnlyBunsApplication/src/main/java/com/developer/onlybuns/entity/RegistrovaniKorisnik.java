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

    public RegistrovaniKorisnik(Integer id, String email, String password, String ime, String prezime, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari) {
        super(id, email, password, ime, prezime, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
    }

    public RegistrovaniKorisnik(String email, String password, String ime, String prezime, String grad, String drzava, String broj, Uloga uloga, List<Objava> objave, List<Komentar> komentari) {
        super(email, password, ime, prezime, grad, drzava, broj, uloga);
        this.objave = objave;
        this.komentari = komentari;
    }
}
