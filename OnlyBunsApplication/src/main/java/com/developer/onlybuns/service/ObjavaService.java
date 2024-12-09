package com.developer.onlybuns.service;


import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Komentar;
import com.developer.onlybuns.entity.Lajk;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;

import java.util.List;
import java.util.Optional;

public interface ObjavaService {
    List<ObjavaDTO> findAllObjavaDTO();

    ObjavaDTO findById(Integer id);

    Optional<Objava> getById(Integer id);

    void saveObjava(Objava objava);

    Objava updateObjava(Objava objava);

    void deleteObjava(Integer id);

    List<Lajk> getAllLajkovi(Integer id);

    List<Komentar> getAllKomentari(Integer id);

    List<ObjavaDTO> findAllUserFollows(String username);

    List<ObjavaDTO> findAllObjavaDTOByUser(String username);

}