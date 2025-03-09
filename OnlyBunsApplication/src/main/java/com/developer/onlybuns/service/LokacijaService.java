package com.developer.onlybuns.service;


import com.developer.onlybuns.entity.Lokacija;

import java.util.List;
import java.util.Optional;

public interface LokacijaService {

    List<Lokacija> findAll();
    Optional<Lokacija> findById(Integer id);
    Lokacija saveLokacija(Lokacija lokacija);
    Lokacija updateLokacija(Lokacija lokacija);
    void deleteLokacija(Integer id);

    Lokacija findByAddress(String ulica, String grad, String drzava);
}
