package com.developer.onlybuns.controller;


import com.developer.onlybuns.dto.request.JwtUtil;
import com.developer.onlybuns.dto.request.LoginDTO;
import com.developer.onlybuns.entity.Korisnik;
import com.developer.onlybuns.service.KorisnikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    private final KorisnikService korisnikService;

    public AuthController(KorisnikService korisnikService) {
        this.korisnikService = korisnikService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginKorisnik(@RequestBody LoginDTO loginDTO) {
        Korisnik validCredentials = korisnikService.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (validCredentials != null) {
            Korisnik korisnik = korisnikService.findByEmail(loginDTO.getEmail());

            String ime = korisnik.getIme();
            String uloga = korisnik.getUloga().toString();

            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.generateToken(loginDTO.getEmail(),ime, uloga);

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Neuspešna prijava. Proverite email i lozinku.");
        }
    }

    @PostMapping("/decodeJwt")
    public ResponseEntity<?> decodeJwt(@RequestBody String token) {
        if (!JwtUtil.validateToken(token)) {
            return ResponseEntity.status(400).body("Invalid JWT token");
        }

        try {
            // Extract korisnik (email) from the JWT token
            String email = JwtUtil.getEmailFromToken(token);

            String name = JwtUtil.getNameFromToken(token);

            String role = JwtUtil.getRoleFromToken(token);

            Map<String, String> response = new HashMap<>();
            response.put("Email", email);
            response.put("Name", name);
            response.put("Role", role);

            // Return the extracted data in the response
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Any other exception
            return ResponseEntity.status(400).body("Error decoding JWT token");
        }
    }
}





