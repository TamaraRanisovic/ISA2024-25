package com.developer.onlybuns.controller;
import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Komentar;
import com.developer.onlybuns.entity.Lajk;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.service.ObjavaService;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/objava")
public class ObjavaController {

    private final ObjavaService objavaService;

    public ObjavaController(ObjavaService objavaService) {
        this.objavaService = objavaService;
    }

    @GetMapping
    public List<ObjavaDTO> findAllObjavaDTO() {
        return objavaService.findAllObjavaDTO();
    }

    @GetMapping("/{id}")
    public Optional<Objava> findById(@PathVariable("id") Integer id) {
        return objavaService.findById(id);
    }

    @PostMapping("/add")
    public Objava save(@RequestBody Objava objava) {
        return objavaService.saveObjava(objava);
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
        return objavaService.findAllUserFollows(username);
    }

}
