package com.developer.onlybuns.controller;

import com.developer.onlybuns.dto.request.LokacijaDTO;
import com.developer.onlybuns.dto.request.LokacijaInfoDTO;
import com.developer.onlybuns.dto.request.NovaObjavaDTO;
import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.entity.Lokacija;
import com.developer.onlybuns.entity.Objava;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.service.GeocodingService;
import com.developer.onlybuns.service.LokacijaService;
import com.developer.onlybuns.service.ObjavaService;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/lokacija")
public class LokacijaController {

    private final LokacijaService lokacijaService;

    private final ObjavaService objavaService;

    private final RegistrovaniKorisnikService registrovaniKorisnikService;

    private final GeocodingService geocodingService;


    public LokacijaController(LokacijaService lokacijaService, ObjavaService objavaService, RegistrovaniKorisnikService registrovaniKorisnikService, GeocodingService geocodingService) {
        this.lokacijaService = lokacijaService;
        this.objavaService = objavaService;
        this.registrovaniKorisnikService = registrovaniKorisnikService;
        this.geocodingService = geocodingService;
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


    @GetMapping("/nearby-posts/{username}")
    public ResponseEntity<List<double[]>> getNearbyPosts(@PathVariable("username") String username) {

        Optional<RegistrovaniKorisnik> registrovaniKorisnik = registrovaniKorisnikService.findByUsername(username);

        if (!registrovaniKorisnik.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Lokacija lokacija = registrovaniKorisnik.get().getLokacija();
        List<ObjavaDTO> nearbyPosts = objavaService.getUsersNearbyPosts(username, lokacija.getG_sirina(), lokacija.getG_duzina());

        if (nearbyPosts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        List<double[]> coordinatesList = new ArrayList<>();

        for (ObjavaDTO objavaDTO : nearbyPosts) {
            LokacijaDTO lokacijaDTO = objavaDTO.getLokacijaDTO();
            String address = lokacijaDTO.getUlica() + ", " + lokacijaDTO.getGrad() + ", " + lokacijaDTO.getDrzava();
            double[] coordinates = geocodingService.getCoordinates(address);
            if (coordinates != null) { // Ensure valid coordinates before adding
                coordinatesList.add(coordinates);
            }
        }

        return ResponseEntity.ok(coordinatesList);
    }
}





