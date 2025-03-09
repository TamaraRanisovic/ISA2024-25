package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.entity.Lokacija;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.repository.LokacijaRepository;
import com.developer.onlybuns.service.LokacijaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LokacijaServiceImpl implements LokacijaService {
    private final LokacijaRepository lokacijaRepository;

    public LokacijaServiceImpl(LokacijaRepository lokacijaRepository) {
        this.lokacijaRepository = lokacijaRepository;
    }

    @Override
    public Optional<Lokacija> findById(Integer id) {
        return lokacijaRepository.findById(id);
    }

    @Override
    public List<Lokacija> findAll() {
        return lokacijaRepository.findAll();
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
    public void deleteLokacija(Integer id) {
        lokacijaRepository.deleteById(id);
    }


    @Override
    public Lokacija findByAddress(String ulica, String grad, String drzava) {
        List<Lokacija> lokacije = findAll();
        for (Lokacija lokacija : lokacije) {
            if (lokacija.getUlica().equals(ulica) && lokacija.getGrad().equals(grad) && lokacija.getDrzava().equals(drzava)) {
               // Lokacija pronadjenaLokacija = new Lokacija(lokacija.getId(), ulica, grad, drzava, lokacija.getG_sirina(), lokacija);
                return lokacija;
            }
        }
        return null;

    }


}
