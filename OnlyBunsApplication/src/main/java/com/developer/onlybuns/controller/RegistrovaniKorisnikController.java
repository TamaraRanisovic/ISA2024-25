package com.developer.onlybuns.controller;
import com.developer.onlybuns.entity.Pratioci;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/registrovaniKorisnik")
public class RegistrovaniKorisnikController {

    private final RegistrovaniKorisnikService registrovaniKorisnikService;

    public RegistrovaniKorisnikController(RegistrovaniKorisnikService registrovaniKorisnikService) {
        this.registrovaniKorisnikService = registrovaniKorisnikService;
    }

    @GetMapping
    public List<RegistrovaniKorisnik> findAllRegistrovaniKorisnik() {
        return registrovaniKorisnikService.findAllRegistrovaniKorisnik();
    }

    @GetMapping("/{id}")
    public Optional<RegistrovaniKorisnik> findRegistrovaniKorisnikById(@PathVariable("id") Integer id) {
        return registrovaniKorisnikService.findById(id);
    }

    @GetMapping("/followers/{username}")
    public List<String> getAllFollowers(@PathVariable("username") String username) {
        return registrovaniKorisnikService.getAllFollowers(username);
    }

    @GetMapping("/following/{username}")
    public List<String> getAllFollowing(@PathVariable("username") String username) {
        return registrovaniKorisnikService.getAllFollowing(username);
    }

    @PostMapping("/add")
    public RegistrovaniKorisnik saveRegistrovaniKorisnik(@RequestBody RegistrovaniKorisnik employeeEntity) {
        return registrovaniKorisnikService.saveRegistrovaniKorisnik(employeeEntity);
    }

    @PostMapping("/login")
    public ResponseEntity<String> prijaviKorisnika(@RequestBody RegistrovaniKorisnik korisnik) {
        RegistrovaniKorisnik validCredentials = registrovaniKorisnikService.proveriKorisnika(korisnik.getEmail(), korisnik.getPassword());
        if (validCredentials != null) {
            return ResponseEntity.ok("{\"message\": \"Uspesna prijava.\"}");
        } else {
            return ResponseEntity.status(401).body("Neuspešna prijava. Proverite email i lozinku.");
        }
    }

    @PutMapping
    public RegistrovaniKorisnik updateRegistrovaniKorisnik(@RequestBody RegistrovaniKorisnik employeeEntity) {
        return registrovaniKorisnikService.updateRegistrovaniKorisnik(employeeEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistrovaniKorisnik(@PathVariable("id") Integer id) {
        registrovaniKorisnikService.deleteRegistrovaniKorisnik(id);
    }

    @GetMapping("/emails")
    public ResponseEntity<List<String>> getAllEmails() {
        List<String> emails = registrovaniKorisnikService.getAllEmails();
        return ResponseEntity.ok(emails);
    }


    @GetMapping("/username")
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = registrovaniKorisnikService.getAllUsernames();
        return ResponseEntity.ok(usernames);
    }

/*    Using Request and Response with save and update employee

    @PostMapping("/res")
    public RegistrovaniKorisnikResponse saveEmpResponse(@RequestBody RegistrovaniKorisnikRequest employeeRequest) {
        return registrovaniKorisnikService.saveRegistrovaniKorisnik(employeeRequest);
    }

    @PutMapping("/res/{id}")
    public RegistrovaniKorisnikResponse updateEmpResponse(@RequestBody RegistrovaniKorisnikRequest employeeRequest, @PathVariable("id") Long id) {
        return registrovaniKorisnikService.updateRegistrovaniKorisnik(employeeRequest, id);
    }
*/
}
