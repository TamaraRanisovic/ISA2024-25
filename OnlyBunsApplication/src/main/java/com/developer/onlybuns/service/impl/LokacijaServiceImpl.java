package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.dto.request.*;
import com.developer.onlybuns.entity.Korisnik;
import com.developer.onlybuns.entity.Lokacija;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.repository.LokacijaRepository;
import com.developer.onlybuns.service.LokacijaService;
import com.developer.onlybuns.service.ObjavaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LokacijaServiceImpl implements LokacijaService {
    private final LokacijaRepository lokacijaRepository;

    private final Logger LOG = LoggerFactory.getLogger(ObjavaServiceImpl.class);


    @Autowired
    @Lazy
    private LokacijaService self;

    public LokacijaServiceImpl(LokacijaRepository lokacijaRepository) {
        this.lokacijaRepository = lokacijaRepository;
    }

    public List<Lokacija> findAll() {
        LOG.info("Fetching all locations from DB (cache not used)");
        return lokacijaRepository.findAll();
    }

    public Optional<Lokacija> findById(Integer id) {
        LOG.info("Location data not found in cache – loading from database");
        return lokacijaRepository.findById(id);
    }

    public List<String> getLokacijaUsernames(Lokacija lokacija) {
        List<Korisnik> korisnici = lokacija.getKorisnici();
        List<String> usernames = new ArrayList<String>();
        for (Korisnik korisnik : korisnici) {
            usernames.add(korisnik.getKorisnickoIme());
        }
        return usernames;
    }



    @Override
    public Lokacija saveLokacija(Lokacija lokacija) {
        return lokacijaRepository.save(lokacija);
    }

    @Override
    public Lokacija updateLokacija(Lokacija lokacija) {
        return lokacijaRepository.save(lokacija);
    }



    @Override
    public Lokacija findByAddress(String ulica, String grad, String drzava) {
        List<Lokacija> lokacije = self.findAll();
        for (Lokacija lokacija : lokacije) {
            if (lokacija.getUlica().equals(ulica) && lokacija.getGrad().equals(grad) && lokacija.getDrzava().equals(drzava)) {
                return lokacija;
            }
        }
        return null;

    }

    public void removeFromCache() {
        LOG.info("Locations removed from cache!");

    }


}
