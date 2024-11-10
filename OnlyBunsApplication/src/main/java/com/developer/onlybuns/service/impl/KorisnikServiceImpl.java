package com.developer.onlybuns.service.impl;


import com.developer.onlybuns.entity.Korisnik;
import com.developer.onlybuns.service.KorisnikService;
import com.developer.onlybuns.repository.KorisnikRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikServiceImpl implements KorisnikService {

    private final KorisnikRepository korisnikRepository;

    public KorisnikServiceImpl(KorisnikRepository korisnikRepository) {
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public Korisnik findByEmailAndPassword(String email, String password) {
        Korisnik korisnik = korisnikRepository.findByEmailAndPassword(email, password);
        return korisnik;
    }

    @Override
    public Korisnik findByEmail(String email) {
        Korisnik korisnik = korisnikRepository.findByEmail(email);
        return korisnik;
    }

    @Override
    public String getKorisnikUloga(String email) {
        Korisnik korisnik = findByEmail(email);
        String uloga = korisnik.getUloga().toString();
        return uloga;
    }
}
