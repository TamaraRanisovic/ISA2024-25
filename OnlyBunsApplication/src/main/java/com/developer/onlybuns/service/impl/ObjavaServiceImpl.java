package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.dto.request.KomentarDTO;
import com.developer.onlybuns.dto.request.LajkDTO;
import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Komentar;
import com.developer.onlybuns.entity.Lajk;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.repository.ObjavaRepository;
import com.developer.onlybuns.repository.RegistrovaniKorisnikRepository;
import com.developer.onlybuns.service.ObjavaService;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ObjavaServiceImpl implements ObjavaService {

    private final ObjavaRepository objavaRepository;


    private final RegistrovaniKorisnikService registrovaniKorisnikService;


    public ObjavaServiceImpl(ObjavaRepository objavaRepository, RegistrovaniKorisnikService registrovaniKorisnikService) {
        this.objavaRepository = objavaRepository;
        this.registrovaniKorisnikService = registrovaniKorisnikService;
    }

    public String getObjavaUsername(Integer id) {
        Optional<Objava> objava = findById(id);
        if (objava != null) {
            String username = objava.get().getRegistrovaniKorisnik().getKorisnickoIme();
            return username;
        } else {
            return null;
        }
    }

    public List<LajkDTO> getObjavaLajkoviDTO(Integer id){
        Optional<Objava> objava = findById(id);
        List<LajkDTO> lajkoviDTO = new ArrayList<LajkDTO>();
        if (objava != null) {
            List<Lajk> lajkovi = objava.get().getLajkovi();
            for (Lajk lajk : lajkovi) {
                LajkDTO lajkDTO = new LajkDTO(lajk.getId(), lajk.getRegistrovaniKorisnik().getEmail(), lajk.getObjava().getId());
                lajkoviDTO.add(lajkDTO);
            }
            return lajkoviDTO;
        } else {
            return lajkoviDTO;
        }
    }

    public List<KomentarDTO> getObjavaKomentariiDTO(Integer id){
        Optional<Objava> objava = findById(id);
        List<KomentarDTO> komentariDTO = new ArrayList<KomentarDTO>();
        if (objava != null) {
            List<Komentar> komentari = objava.get().getKomentari();
            for (Komentar komentar : komentari) {
                KomentarDTO komentarDTO = new KomentarDTO(komentar.getId(), komentar.getOpis(), komentar.getRegistrovaniKorisnik().getEmail(), komentar.getDatum_kreiranja(), komentar.getObjava().getId());
                komentariDTO.add(komentarDTO);
            }
            return komentariDTO;
        } else {
            return komentariDTO;
        }
    }

    @Override
    public List<ObjavaDTO> findAllObjavaDTO() {
        List<Objava> objave = objavaRepository.findAll();
        List<ObjavaDTO> objaveDTO = new ArrayList<ObjavaDTO>();
        for (Objava objava : objave) {
            String korisnicko_ime = getObjavaUsername(objava.getId());
            List<LajkDTO> lajkoviDTO = getObjavaLajkoviDTO(objava.getId());
            List<KomentarDTO> komentariDTO = getObjavaKomentariiDTO(objava.getId());
            Integer broj_lajkova = lajkoviDTO.size();
            Integer broj_komentara = komentariDTO.size();
            ObjavaDTO objavaDTO = new ObjavaDTO(objava.getId(), objava.getOpis(), objava.getSlika(), objava.getG_sirina(), objava.getG_duzina(), objava.getDatum_objave(), korisnicko_ime, komentariDTO, lajkoviDTO, broj_lajkova, broj_komentara);
            objaveDTO.add(objavaDTO);
        }
        objaveDTO.sort(Comparator.comparing(ObjavaDTO::getDatum_objave).reversed());

        return objaveDTO;
    }

    @Override
    public List<ObjavaDTO> findAllObjavaDTOByUser(String username) {
        List<Objava> objave = objavaRepository.findAll();
        List<ObjavaDTO> objaveDTO = new ArrayList<ObjavaDTO>();
        for (Objava objava : objave) {
            String korisnicko_ime = getObjavaUsername(objava.getId());
            if (korisnicko_ime.equals(username)) {
                List<LajkDTO> lajkoviDTO = getObjavaLajkoviDTO(objava.getId());
                List<KomentarDTO> komentariDTO = getObjavaKomentariiDTO(objava.getId());
                Integer broj_lajkova = lajkoviDTO.size();
                Integer broj_komentara = komentariDTO.size();
                ObjavaDTO objavaDTO = new ObjavaDTO(objava.getId(), objava.getOpis(), objava.getSlika(), objava.getG_sirina(), objava.getG_duzina(), objava.getDatum_objave(), korisnicko_ime, komentariDTO, lajkoviDTO, broj_lajkova, broj_komentara);
                objaveDTO.add(objavaDTO);
            }
        }
        return objaveDTO;
    }

    @Override
    public Optional<Objava> findById(Integer id) {
        return objavaRepository.findById(id);
    }

    @Override
    public void saveObjava(Objava objava) {
        objavaRepository.save(objava);
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

    @Override
    public List<ObjavaDTO> findAllUserFollows(String username) {
        List<ObjavaDTO> objave = new ArrayList<ObjavaDTO>();
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = registrovaniKorisnikService.findByUsername(username);
        if (registrovaniKorisnik != null) {
            List<String> following = registrovaniKorisnikService.getAllFollowing(username);
            for (String user : following) {
                List<ObjavaDTO> korisnikObjave = findAllObjavaDTOByUser(user);
                objave.addAll(korisnikObjave);
            }
            objave.sort(Comparator.comparing(ObjavaDTO::getDatum_objave).reversed());

            return objave;
        } else {
            return objave;
        }
    }

}