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

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pratioci> following;

    @OneToMany(mappedBy = "followed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pratioci> followers;

    public RegistrovaniKorisnik() {

    }

    public RegistrovaniKorisnik(List<Objava> objave, List<Komentar> komentari, List<Pratioci> following, List<Pratioci> followers) {
        this.objave = objave;
        this.komentari = komentari;
        this.following = following;
        this.followers = followers;
    }

    public RegistrovaniKorisnik(Integer id, String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari, List<Pratioci> following, List<Pratioci> followers) {
        super(id, korisnicko_ime, email, password, ime, prezime, ulica_broj, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
        this.following = following;
        this.followers = followers;
    }

    public RegistrovaniKorisnik(String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari, List<Pratioci> following, List<Pratioci> followers) {
        super(korisnicko_ime, email, password, ime, prezime, ulica_broj, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
        this.following = following;
        this.followers = followers;
    }

    public List<Objava> getObjave() {
        return objave;
    }

    public void setObjave(List<Objava> objave) {
        this.objave = objave;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public List<Pratioci> getFollowing() {
        return following;
    }

    public void setFollowing(List<Pratioci> following) {
        this.following = following;
    }

    public List<Pratioci> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Pratioci> followers) {
        this.followers = followers;
    }
}
