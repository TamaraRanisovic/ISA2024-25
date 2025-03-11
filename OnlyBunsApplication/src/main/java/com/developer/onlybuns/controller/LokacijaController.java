package com.developer.onlybuns.controller;

import com.developer.onlybuns.dto.request.LokacijaInfoDTO;
import com.developer.onlybuns.dto.request.NovaObjavaDTO;
import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Lokacija;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.service.LokacijaService;
import com.developer.onlybuns.service.ObjavaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/lokacija")
public class LokacijaController {

    private final LokacijaService lokacijaService;

    private final ObjavaService objavaService;

    public LokacijaController(LokacijaService lokacijaService, ObjavaService objavaService) {
        this.lokacijaService = lokacijaService;
        this.objavaService = objavaService;
    }

    @GetMapping
    public List<LokacijaInfoDTO> findAllLokacijaInfoDTO() {
        return objavaService.findAllLokacijaInfoDTO();
    }

    @GetMapping("/{id}")
    public  ResponseEntity<LokacijaInfoDTO> findLokacijaInfoDTOById(@PathVariable("id") Integer id) {
        LokacijaInfoDTO lokacijaInfoDTO = objavaService.findLokacijaInfoDTOById(id);
        if (lokacijaInfoDTO != null) {
            return ResponseEntity.ok(lokacijaInfoDTO);
        } else {
            return (ResponseEntity<LokacijaInfoDTO>) ResponseEntity.notFound();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> save(@RequestBody Lokacija lokacija) {
            lokacijaService.saveLokacija(lokacija);
            return ResponseEntity.ok("{\"message\": \"Uspesno kreirana nova lokacija.\"}");
    }

    @PutMapping
    public Lokacija update(@RequestBody Lokacija lokacija) {
        return lokacijaService.updateLokacija(lokacija);
    }



    @GetMapping(value = "/removeCache")
    public ResponseEntity<String> removeFromCache() {
        lokacijaService.removeFromCache();
        return ResponseEntity.ok("Posts successfully removed from cache!");
    }
}





