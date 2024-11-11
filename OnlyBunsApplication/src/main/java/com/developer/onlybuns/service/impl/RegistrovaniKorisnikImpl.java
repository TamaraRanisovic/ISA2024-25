package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.entity.Pratioci;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.repository.RegistrovaniKorisnikRepository;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.stereotype.Service;

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
/*   Using Request and Response with save and update registrovaniKorisnik

    @Override
    public RegistrovaniKorisnikResponse saveRegistrovaniKorisnik(RegistrovaniKorisnikRequest registrovaniKorisnikRequest) {
        RegistrovaniKorisnik registrovaniKorisnikEntity = RegistrovaniKorisnikMapper.MAPPER.fromRequestToEntity(registrovaniKorisnikRequest);
        registrovaniKorisnikRepository.save(registrovaniKorisnikEntity);
        return RegistrovaniKorisnikMapper.MAPPER.fromEntityToResponse(registrovaniKorisnikEntity);
    }

    @Override
    public RegistrovaniKorisnikResponse updateRegistrovaniKorisnik(RegistrovaniKorisnikRequest registrovaniKorisnikRequest, Integer id) {

        Optional<RegistrovaniKorisnik> checkExistingRegistrovaniKorisnik = findById(id);
        if (! checkExistingRegistrovaniKorisnik.isPresent())
            throw new RuntimeException("RegistrovaniKorisnik Id "+ id + " Not Found!");

        RegistrovaniKorisnik registrovaniKorisnikEntity = RegistrovaniKorisnikMapper.MAPPER.fromRequestToEntity(registrovaniKorisnikRequest);
        registrovaniKorisnikEntity.setId(id);
        registrovaniKorisnikRepository.save(registrovaniKorisnikEntity);
        return RegistrovaniKorisnikMapper.MAPPER.fromEntityToResponse(registrovaniKorisnikEntity);
    }
*/
}
