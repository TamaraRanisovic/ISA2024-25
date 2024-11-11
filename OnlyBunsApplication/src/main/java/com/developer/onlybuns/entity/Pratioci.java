package com.developer.onlybuns.entity;

import javax.persistence.*;

@Entity
@Table(name = "pratioci", uniqueConstraints = @UniqueConstraint(columnNames = {"following_id", "followed_id"}))
public class Pratioci {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private RegistrovaniKorisnik following;

    @ManyToOne
    @JoinColumn(name = "followed_id", nullable = false)
    private RegistrovaniKorisnik followed;

    // Constructors, Getters, and Setters

    public Pratioci() {
    }

    public Pratioci(RegistrovaniKorisnik following, RegistrovaniKorisnik followed) {
        this.following = following;
        this.followed = followed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RegistrovaniKorisnik getFollowing() {
        return following;
    }

    public void setFollowing(RegistrovaniKorisnik following) {
        this.following = following;
    }

    public RegistrovaniKorisnik getFollowed() {
        return followed;
    }

    public void setFollowed(RegistrovaniKorisnik followed) {
        this.followed = followed;
    }
}