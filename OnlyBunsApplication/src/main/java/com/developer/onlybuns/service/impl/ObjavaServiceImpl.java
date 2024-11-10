package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.entity.Komentar;
import com.developer.onlybuns.entity.Lajk;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.repository.ObjavaRepository;
import com.developer.onlybuns.service.ObjavaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObjavaServiceImpl implements ObjavaService {

    private final ObjavaRepository objavaRepository;

    public ObjavaServiceImpl(ObjavaRepository objavaRepository) {
        this.objavaRepository = objavaRepository;
    }

    @Override
    public List<Objava> findAll() {
        return objavaRepository.findAll();
    }

    @Override
    public Optional<Objava> findById(Integer id) {
        return objavaRepository.findById(id);
    }

    @Override
    public Objava saveObjava(Objava objava) {
        return objavaRepository.save(objava);
    }

    @Override
    public Objava updateObjava(Objava objava) {
        return objavaRepository.save(objava);
    }

    @Override
    public void deleteObjava(Integer id) {
        objavaRepository.deleteById(id);
    }

    @Override
    public List<Lajk> getAllLajkovi(Integer id) {
        Optional<Objava> objava = findById(id);
        List<Lajk> lajkovi = new ArrayList<>();
        if (objava != null) {
            lajkovi = objava.get().getLajkovi();
            return lajkovi;
        } else {
            return lajkovi;
        }
    }

    @Override
    public List<Komentar> getAllKomentari(Integer id) {
        Optional<Objava> objava = findById(id);
        List<Komentar> komentari = new ArrayList<>();
        if (objava != null) {
            komentari = objava.get().getKomentari();
            return komentari;
        } else {
            return komentari;
        }
    }

}