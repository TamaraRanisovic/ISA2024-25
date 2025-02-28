package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.dto.request.ObjavaDTO;
import com.developer.onlybuns.dto.request.RegistrovaniKorisnikDTO;
import com.developer.onlybuns.dto.request.SevenDaysReportDTO;
import com.developer.onlybuns.entity.Pratioci;
import com.developer.onlybuns.entity.RegistrovaniKorisnik;
import com.developer.onlybuns.enums.Uloga;
import com.developer.onlybuns.repository.RegistrovaniKorisnikRepository;
import com.developer.onlybuns.service.ObjavaService;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrovaniKorisnikImpl implements RegistrovaniKorisnikService {

    private final RegistrovaniKorisnikRepository registrovaniKorisnikRepository;

    private final ObjavaService objavaService;

    @Autowired
    private JavaMailSender mailSender;

    public RegistrovaniKorisnikImpl(RegistrovaniKorisnikRepository registrovaniKorisnikRepository, ObjavaService objavaService) {
        this.registrovaniKorisnikRepository = registrovaniKorisnikRepository;
        this.objavaService = objavaService;
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
    public int getNewFollowersCount(String username, LocalDateTime fromDate) {
        return registrovaniKorisnikRepository.countNewFollowers(username, fromDate);
    }

    @Override
    public SevenDaysReportDTO generateSevenDaysReport(String username, LocalDateTime lastLogin) {
        int newFollowersCount = getNewFollowersCount(username, lastLogin);
        int newCommentsCount = objavaService.countNewCommentsOnUserPosts(username, lastLogin);
        int newLikesCount = objavaService.countNewLikesOnUserPosts(username, lastLogin);

        SevenDaysReportDTO sevenDaysReportDTO = new SevenDaysReportDTO(newFollowersCount, newCommentsCount, newLikesCount);
        return sevenDaysReportDTO;

    }

    @Override
    public List<String> findInactiveUsers(LocalDateTime fromDate) {
        return registrovaniKorisnikRepository.findInactiveUsers(fromDate);
    }

    @Override
    public List<ObjavaDTO> findAllUserFollows(String username) {
        List<ObjavaDTO> objave = new ArrayList<ObjavaDTO>();
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = findByUsername(username);
        if (registrovaniKorisnik != null) {
            List<String> following = getAllFollowing(username);
            for (String user : following) {
                List<ObjavaDTO> korisnikObjave = objavaService.findAllObjavaDTOByUser(user);
                objave.addAll(korisnikObjave);
            }
            objave.sort(Comparator.comparing(ObjavaDTO::getDatum_objave).reversed());

            return objave;
        } else {
            return objave;
        }
    }

    @Override
    public void sendSevenDaysReportEmail(String email, String username, SevenDaysReportDTO sevenDaysReportDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ranisovic.in1.2020@uns.ac.rs");
        message.setTo(email);
        message.setSubject("Your OnlyBunsApp Activity Report");
        message.setText("Dear " + username + "," +
                "\n" +
                "We noticed you haven't been active on OnlyBunsApp for a while, and we wanted to share a quick summary of what you've missed since your last visit:\n" +
                "\n" +
                "New Followers:" + sevenDaysReportDTO.getNewFollowersCount() +
                "\nNew Likes on Your Posts:" + sevenDaysReportDTO.getNewLikesCount() +
                "\nNew Comments on Your Posts:" + sevenDaysReportDTO.getNewCommentsCount() +
                "\n\nYour community is engaging with your content, and we’d love to see you back! Log in now to connect with your followers and check out the latest updates.\n" +
                "\n" +
                "We hope to see you soon!\n" +
                "\n" +
                "Warm regards,\n" +
                "The OnlyBunsApp Team");
        mailSender.send(message);
    }
    private void sendActivationEmail(String email, String token) {
        String activationLink = "http://localhost:8080/registrovaniKorisnik/activate?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ranisovic.in1.2020@uns.ac.rs");
        message.setTo(email);
        message.setSubject("Activate your account");
        message.setText("Click the following link to activate your account: " + activationLink);
        mailSender.send(message);
    }
    @Transactional
    @Override
    @Scheduled(cron = "0 */1 * * * *") // Currently: runs every 1min, change to: run every day at 9 AM
    public void sendNotificationsToInactiveUsers() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<String> inactiveUsers = findInactiveUsers(sevenDaysAgo);
        for (String username : inactiveUsers) {
            SevenDaysReportDTO report = generateSevenDaysReport(username, sevenDaysAgo);
            if (report != null) {
                Optional<RegistrovaniKorisnik> registrovaniKorisnik = findByUsername(username);
                sendSevenDaysReportEmail(registrovaniKorisnik.get().getEmail(), registrovaniKorisnik.get().getKorisnickoIme(), report);
            }
        }
    }
    @Override
    public RegistrovaniKorisnikDTO getKorisnikDTOByUsername(String username) {
        Optional<RegistrovaniKorisnik> registrovaniKorisnik = findByUsername(username);
        if (registrovaniKorisnik != null) {
            Integer num_followers = getAllFollowers(username).size();
            Integer num_following = getAllFollowing(username).size();
            Integer num_posts = objavaService.findAllObjavaDTOByUser(username).size();
            RegistrovaniKorisnikDTO registrovaniKorisnikDTO = new RegistrovaniKorisnikDTO(username, num_followers, num_following, num_posts);
            return registrovaniKorisnikDTO;
        }
        return null;
    }


}
