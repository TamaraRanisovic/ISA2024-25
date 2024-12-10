package com.developer.onlybuns.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT COUNT(*) FROM Pratioci f WHERE f.followed.id = :userId AND f.datum_pracenja > :fromDate")
    int countNewFollowers(@Param("userId") Integer userId, @Param("fromDate") LocalDateTime fromDate);

    @Query("SELECT COUNT(*) FROM Lajk l WHERE l.registrovaniKorisnik.id = :userId AND l.datum_lajkovanja >= :fromDate")
    int countNewLikes(@Param("userId") Integer userId, @Param("fromDate") LocalDateTime fromDate);


}


