package com.developer.onlybuns.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="objava")
public class Objava {

    @Id
    @SequenceGenerator(name = "mySeqGenV1", sequenceName = "mySeqV1", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mySeqGenV1")
    private Integer id;

    @Column(name="opis", nullable=false)
    private String opis;

    @Column(name="slika", nullable=false)
    private String slika;

    @Column(name = "g_sirina", nullable = false)
    private Double g_sirina;  // To store latitude coordinate

    @Column(name = "g_duzina", nullable = false)
    private Double g_duzina;

    @Column(name="datum_objave", nullable=false)
    private LocalDateTime datum_objave;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", nullable = false)
    private RegistrovaniKorisnik registrovaniKorisnik;

    @OneToMany(mappedBy = "objava", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Komentar> komentari;

    @OneToMany(mappedBy = "objava", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Lajk> lajkovi;

    public Objava() {

    }

    public Objava(Integer id, String opis, String slika, Double g_sirina, Double g_duzina, LocalDateTime datum_objave, RegistrovaniKorisnik registrovaniKorisnik, List<Komentar> komentari, List<Lajk> lajkovi) {
        this.id = id;
        this.opis = opis;
        this.slika = slika;
        this.g_sirina = g_sirina;
        this.g_duzina = g_duzina;
        this.datum_objave = datum_objave;
        this.registrovaniKorisnik = registrovaniKorisnik;
        this.komentari = komentari;
        this.lajkovi = lajkovi;
    }

    public Objava(String opis, String slika, Double g_sirina, Double g_duzina, LocalDateTime datum_objave, RegistrovaniKorisnik registrovaniKorisnik, List<Komentar> komentari, List<Lajk> lajkovi) {
        this.opis = opis;
        this.slika = slika;
        this.g_sirina = g_sirina;
        this.g_duzina = g_duzina;
        this.datum_objave = datum_objave;
        this.registrovaniKorisnik = registrovaniKorisnik;
        this.komentari = komentari;
        this.lajkovi = lajkovi;
    }

    public Objava(String opis, String slika, Double g_sirina, Double g_duzina, LocalDateTime datum_objave, RegistrovaniKorisnik registrovaniKorisnik) {
        this.opis = opis;
        this.slika = slika;
        this.g_sirina = g_sirina;
        this.g_duzina = g_duzina;
        this.datum_objave = datum_objave;
        this.registrovaniKorisnik = registrovaniKorisnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RegistrovaniKorisnik getRegistrovaniKorisnik() {
        return registrovaniKorisnik;
    }

    public void setRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik) {
        this.registrovaniKorisnik = registrovaniKorisnik;
    }

    public List<Komentar> getKomentari() {
        return komentari;
    }

    public void setKomentari(List<Komentar> komentari) {
        this.komentari = komentari;
    }

    public List<Lajk> getLajkovi() {
        return lajkovi;
    }

    public void setLajkovi(List<Lajk> lajkovi) {
        this.lajkovi = lajkovi;
    }
}
