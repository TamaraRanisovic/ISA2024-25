package com.developer.onlybuns.service;
import com.developer.onlybuns.entity.Pratioci;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RegistrovaniKorisnikService {
    List<RegistrovaniKorisnik> findAllRegistrovaniKorisnik();
    Optional<RegistrovaniKorisnik> findById(Integer id);

    Optional<RegistrovaniKorisnik> findByUsername(String username);

    RegistrovaniKorisnik saveRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik);
    RegistrovaniKorisnik updateRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnik);
    void deleteRegistrovaniKorisnik(Integer id);
    List<String> getAllEmails();

    List<String> getAllUsernames();

    List<String> getAllFollowers(String username);

    List<String> getAllFollowing(String username);

    void register(RegistrovaniKorisnik registrovaniKorisnik, String activationToken);

    boolean activateAccount(String token);

    RegistrovaniKorisnik proveriKorisnika(String email, String password);

    public boolean usernameExists(String username);

    public int getNewFollowersCount(Integer userId, LocalDateTime fromDate);

    public int getNewLikesCount(Integer userId, LocalDateTime fromDate);
}
