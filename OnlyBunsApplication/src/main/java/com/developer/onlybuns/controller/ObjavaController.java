package com.developer.onlybuns.controller;
import com.developer.onlybuns.dto.request.NovaObjavaDTO;
import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.*;
import com.developer.onlybuns.service.ObjavaService;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/objava")
public class ObjavaController {

    private final ObjavaService objavaService;

    private final RegistrovaniKorisnikService registrovaniKorisnikService;


    public ObjavaController(ObjavaService objavaService, RegistrovaniKorisnikService registrovaniKorisnikService) {
        this.objavaService = objavaService;
        this.registrovaniKorisnikService = registrovaniKorisnikService;
    }

    @GetMapping
    public List<ObjavaDTO> findAllObjavaDTO() {
        return objavaService.findAllObjavaDTO();
    }

    @GetMapping("/user/{username}")
    public List<ObjavaDTO> findAllObjavaDTOByUser(@PathVariable("username") String username) {
        return objavaService.findAllObjavaDTOByUser(username);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ObjavaDTO> findById(@PathVariable("id") Integer id) {
        ObjavaDTO objavaDTO = objavaService.findById(id);
        if (objavaDTO != null) {
            return ResponseEntity.ok(objavaDTO);
        } else {
            return (ResponseEntity<ObjavaDTO>) ResponseEntity.notFound();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> save(@RequestBody NovaObjavaDTO objavaDTO) {
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = registrovaniKorisnikService.findByUsername(objavaDTO.getKorisnicko_ime());
        if (registrovaniKorisnik != null) {
            List<Komentar> komentari = new ArrayList<Komentar>();
            List<Lajk> lajkovi = new ArrayList<Lajk>();
            Lokacija lokacija = new Lokacija(objavaDTO.getG_sirina(), objavaDTO.getG_duzina());
            Objava objava = new Objava(objavaDTO.getOpis(), objavaDTO.getSlika(), objavaDTO.getDatum_objave(), registrovaniKorisnik.get(), lokacija, komentari, lajkovi);
            objavaService.saveObjava(objava);

            return ResponseEntity.ok("{\"message\": \"Uspesno kreiran novi post.\"}");
        } else {
            return ResponseEntity.status(401).body("Neuspešno kreiranje novog posta. Proverite unete podatke.");
        }

    }

    @PutMapping
    public Objava update(@RequestBody Objava objava) {
        return objavaService.updateObjava(objava);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        objavaService.deleteObjava(id);
    }

    @GetMapping("/lajkovi")
    public ResponseEntity<List<Lajk>> getAllLajkovi(Integer id) {
        List<Lajk> lajkovi = objavaService.getAllLajkovi(id);
        return ResponseEntity.ok(lajkovi);
    }

    @GetMapping("/komentari")
    public ResponseEntity<List<Komentar>> getAllKomentari(Integer id) {
        List<Komentar> komentari = objavaService.getAllKomentari(id);
        return ResponseEntity.ok(komentari);
    }

    @GetMapping("/feed/{username}")
    public List<ObjavaDTO> findAllUserFollows(@PathVariable("username") String username) {
        return registrovaniKorisnikService.findAllUserFollows(username);
    }

    @GetMapping("/count-newcomments")
    public ResponseEntity<?> countNewFollowers() {
        try {
            int newFollowers = objavaService.countNewCommentsOnUserPosts("user1", LocalDateTime.parse("2024-12-07T10:00:00"));
            return ResponseEntity.ok(newFollowers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/count-newlikes")
    public ResponseEntity<?> countNewLikes() {
        try {
            int newLikes = objavaService.countNewLikesOnUserPosts("user1", LocalDateTime.parse("2024-12-07T16:19:00"));
            return ResponseEntity.ok(newLikes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping(value = "/removeCache")
    public ResponseEntity<String> removeFromCache() {
        objavaService.removeFromCache();
        return ResponseEntity.ok("Posts successfully removed from cache!");
    }

}
