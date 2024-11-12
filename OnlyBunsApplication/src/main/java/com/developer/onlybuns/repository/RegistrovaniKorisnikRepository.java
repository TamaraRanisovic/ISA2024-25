package com.developer.onlybuns.repository;

import java.util.List;
import java.util.Optional;

import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrovaniKorisnikRepository extends JpaRepository<RegistrovaniKorisnik, Integer> {
    @Query("SELECT email FROM RegistrovaniKorisnik")
    List<String> findAllEmails();

    @Query("SELECT korisnickoIme FROM RegistrovaniKorisnik")
    List<String> findAllUsernames();
    
    RegistrovaniKorisnik findByEmailAndPassword(String email, String password);

    Optional<RegistrovaniKorisnik> findByKorisnickoIme(String username);

    Optional<RegistrovaniKorisnik> findByActivationToken(String activationToken);

}
