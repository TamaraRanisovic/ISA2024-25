package com.developer.onlybuns.service;


import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Komentar;
import com.developer.onlybuns.entity.Lajk;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ObjavaService {
    List<ObjavaDTO> findAllObjavaDTO();

    @Cacheable("objavaCache")
    ObjavaDTO findById(Integer id);

    @CacheEvict(cacheNames = {"objavaCache"}, allEntries = true)
    void removeFromCache();

    Optional<Objava> getById(Integer id);

    void saveObjava(Objava objava);

    Objava updateObjava(Objava objava);

    void deleteObjava(Integer id);

    List<Lajk> getAllLajkovi(Integer id);

    List<Komentar> getAllKomentari(Integer id);


    List<ObjavaDTO> findAllObjavaDTOByUser(String username);

    public int countNewCommentsOnUserPosts(String username, LocalDateTime fromDate);

    public int countNewLikesOnUserPosts(String username, LocalDateTime fromDate);
}