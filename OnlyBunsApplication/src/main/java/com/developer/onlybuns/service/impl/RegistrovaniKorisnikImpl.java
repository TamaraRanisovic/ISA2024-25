package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.entity.Pratioci;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.enums.Uloga;
import com.developer.onlybuns.repository.RegistrovaniKorisnikRepository;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrovaniKorisnikImpl implements RegistrovaniKorisnikService {

    private final RegistrovaniKorisnikRepository registrovaniKorisnikRepository;

    public RegistrovaniKorisnikImpl(RegistrovaniKorisnikRepository registrovaniKorisnikRepository) {
        this.registrovaniKorisnikRepository = registrovaniKorisnikRepository;
    }

    @Override
    public List<RegistrovaniKorisnik> findAllRegistrovaniKorisnik() {
        return registrovaniKorisnikRepository.findAll();
    }

    @Override
    public Optional<RegistrovaniKorisnik> findById(Integer id) {
        return registrovaniKorisnikRepository.findById(id);
    }

    @Override
    public Optional<RegistrovaniKorisnik> findByUsername(String username) {
        return registrovaniKorisnikRepository.findByKorisnickoIme(username);
    }

    public void register(RegistrovaniKorisnik registrovaniKorisnik, String activationToken) {
        // Map UserRegistrationDto to User entity, set the token and inactive status
        RegistrovaniKorisnik noviKorisnik = new RegistrovaniKorisnik();
        noviKorisnik.setKorisnickoIme(registrovaniKorisnik.getKorisnickoIme());
        noviKorisnik.setEmail(registrovaniKorisnik.getEmail());
        noviKorisnik.setPassword(registrovaniKorisnik.getPassword());
        noviKorisnik.setIme(registrovaniKorisnik.getIme());
        noviKorisnik.setPrezime(registrovaniKorisnik.getPrezime());
        noviKorisnik.setUlica_broj(registrovaniKorisnik.getUlica_broj());
        noviKorisnik.setGrad(registrovaniKorisnik.getGrad());
        noviKorisnik.setDrzava(registrovaniKorisnik.getDrzava());
        noviKorisnik.setBroj(registrovaniKorisnik.getBroj());
        noviKorisnik.setUloga(Uloga.REGISTROVANI_KORISNIK);
        noviKorisnik.setActivationToken(activationToken);
        noviKorisnik.setVerifikacija(false);

        registrovaniKorisnikRepository.save(noviKorisnik);
    }

    public boolean activateAccount(String token) {
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = registrovaniKorisnikRepository.findByActivationToken(token);
        if (registrovaniKorisnik != null) {
            registrovaniKorisnik.get().setVerifikacija(true); // Activate account
            registrovaniKorisnik.get().setActivationToken(null); // Clear the token after activation
            registrovaniKorisnikRepository.save(registrovaniKorisnik.get());
            return true;
        }
        return false;
    }

    @Override
    public RegistrovaniKorisnik saveRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnikEntity) {
        return registrovaniKorisnikRepository.save(registrovaniKorisnikEntity);
    }

    @Override
    public RegistrovaniKorisnik updateRegistrovaniKorisnik(RegistrovaniKorisnik registrovaniKorisnikEntity) {
        return registrovaniKorisnikRepository.save(registrovaniKorisnikEntity);
    }

    @Override
    public void deleteRegistrovaniKorisnik(Integer id) {
        registrovaniKorisnikRepository.deleteById(id);
    }

    @Override
    public List<String> getAllEmails() {
        return registrovaniKorisnikRepository.findAllEmails();
    }

    @Override
    public List<String> getAllUsernames() {
        return registrovaniKorisnikRepository.findAllUsernames();
    }

    @Override
    public boolean usernameExists(String username) {
        List<String> allUsernames = registrovaniKorisnikRepository.findAllUsernames();
        if (allUsernames.contains(username)) {
            return true;
        }
        return false;
    }



    @Override
    public List<String> getAllFollowers(String username) {
        List<Pratioci> followers = new ArrayList<Pratioci>();
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = findByUsername(username);
        List<String> usernames = new ArrayList<String>();
        if (registrovaniKorisnik != null) {
            followers = registrovaniKorisnik.get().getFollowers();
            for (Pratioci pratilac : followers) {
                usernames.add(pratilac.getFollowing().getKorisnickoIme());
            }
            return usernames;
        } else {
            return usernames;
        }
    }

    @Override
    public List<String> getAllFollowing(String username) {
        List<Pratioci> following = new ArrayList<Pratioci>();
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = findByUsername(username);
        List<String> usernames = new ArrayList<String>();
        if (registrovaniKorisnik != null) {
            following = registrovaniKorisnik.get().getFollowing();
            for (Pratioci pratilac : following) {
                usernames.add(pratilac.getFollowed().getKorisnickoIme());
            }
            return usernames;
        } else {
            return usernames;
        }
    }

    @Override
    public RegistrovaniKorisnik proveriKorisnika(String email, String password) {
        RegistrovaniKorisnik korisnik = registrovaniKorisnikRepository.findByEmailAndPassword(email, password);
        return korisnik;
    }

    @Override
    public int getNewFollowersCount(Integer userId, LocalDateTime fromDate) {
        return registrovaniKorisnikRepository.countNewFollowers(userId, fromDate);
    }

    @Override
    public int getNewLikesCount(Integer userId, LocalDateTime fromDate) {
        return registrovaniKorisnikRepository.countNewLikes(userId, fromDate);
    }




}
