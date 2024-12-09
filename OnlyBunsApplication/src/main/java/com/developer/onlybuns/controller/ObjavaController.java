package com.developer.onlybuns.controller;
import com.developer.onlybuns.dto.request.NovaObjavaDTO;
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

    private final RegistrovaniKorisnikService registrovaniKorisnikService;


    public ObjavaController(ObjavaService objavaService, RegistrovaniKorisnikService registrovaniKorisnikService) {
        this.objavaService = objavaService;
        this.registrovaniKorisnikService = registrovaniKorisnikService;
    }

    @GetMapping
    public List<ObjavaDTO> findAllObjavaDTO() {
        return objavaService.findAllObjavaDTO();
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
            Objava objava = new Objava(objavaDTO.getOpis(), objavaDTO.getSlika(), objavaDTO.getG_sirina(), objavaDTO.getG_duzina(), objavaDTO.getDatum_objave(), registrovaniKorisnik.get(), komentari, lajkovi);
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
        return objavaService.findAllUserFollows(username);
    }

}
