package com.developer.onlybuns.entity;



import com.developer.onlybuns.enums.Uloga;

import javax.persistence.*;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Table(name = "korisnik")
@Inheritance(strategy=TABLE_PER_CLASS)
public class Korisnik {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name="korisnicko_ime", unique=true, nullable=false)
    private String korisnicko_ime;

    @Column(name="email", unique=true, nullable=false)
    private String email;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="ime", nullable=false)
    private String ime;

    @Column(name="prezime", nullable=false)
    private String prezime;

    @Column(name="ulica_broj", nullable=false)
    private String  ulica_broj;

    @Column(name="grad", nullable=false)
    private String  grad;

    @Column(name="drzava", nullable=false)
    private String  drzava;

    @Column(name="broj", nullable=false)
    private String  broj;

    @Enumerated(EnumType.STRING)
    @Column(name="uloga", nullable = false)
    private Uloga uloga;

    @Column(name="verifikacija", nullable = false)
    private boolean verifikacija;



    public Korisnik() {
    }

    public Korisnik(Integer id, String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija) {
        this.id = id;
        this.korisnicko_ime = korisnicko_ime;
        this.email = email;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.ulica_broj = ulica_broj;
        this.grad = grad;
        this.drzava = drzava;
        this.broj = broj;
        this.uloga = uloga;
        this.verifikacija = verifikacija;
    }

    public Korisnik(String korisnicko_ime, String email, String password, String ime, String prezime, String ulica_broj, String grad, String drzava, String broj, Uloga uloga, boolean verifikacija) {
        this.korisnicko_ime = korisnicko_ime;
        this.email = email;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
        this.ulica_broj = ulica_broj;
        this.grad = grad;
        this.drzava = drzava;
        this.broj = broj;
        this.uloga = uloga;
        this.verifikacija = verifikacija;
    }

    public boolean isVerifikacija() {
        return verifikacija;
    }

    public void setVerifikacija(boolean verifikacija) {
        this.verifikacija = verifikacija;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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

    public String getBroj() {
        return broj;
    }

    public void setBroj(String broj) {
        this.broj = broj;
    }

    public String getKorisnicko_ime() {
        return korisnicko_ime;
    }

    public void setKorisnicko_ime(String korisnicko_ime) {
        this.korisnicko_ime = korisnicko_ime;
    }

    public String getUlica_broj() {
        return ulica_broj;
    }

    public void setUlica_broj(String ulica_broj) {
        this.ulica_broj = ulica_broj;
    }
}