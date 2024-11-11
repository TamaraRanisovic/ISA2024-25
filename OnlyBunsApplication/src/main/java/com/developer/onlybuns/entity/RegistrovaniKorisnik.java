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

    @ManyToMany
    @JoinTable(
            name = "korisnik_follows",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<RegistrovaniKorisnik> following;

    // Followers (users following this user)
    @ManyToMany(mappedBy = "following")
    private List<RegistrovaniKorisnik> followers;

    public RegistrovaniKorisnik() {

    }

    public RegistrovaniKorisnik(List<Objava> objave, List<Komentar> komentari, List<RegistrovaniKorisnik> following, List<RegistrovaniKorisnik> followers) {
        this.objave = objave;
        this.komentari = komentari;
        this.following = following;
        this.followers = followers;
    }

    public RegistrovaniKorisnik(Integer id, String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari, List<RegistrovaniKorisnik> following, List<RegistrovaniKorisnik> followers) {
        super(id, korisnicko_ime, email, password, ime, prezime, ulica_broj, grad, drzava, broj, uloga, verifikacija);
        this.objave = objave;
        this.komentari = komentari;
        this.following = following;
        this.followers = followers;
    }

    public RegistrovaniKorisnik(String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija, List<Objava> objave, List<Komentar> komentari, List<RegistrovaniKorisnik> following, List<RegistrovaniKorisnik> followers) {
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

    public List<RegistrovaniKorisnik> getFollowing() {
        return following;
    }

    public void setFollowing(List<RegistrovaniKorisnik> following) {
        this.following = following;
    }

    public List<RegistrovaniKorisnik> getFollowers() {
        return followers;
    }

    public void setFollowers(List<RegistrovaniKorisnik> followers) {
        this.followers = followers;
    }
}
