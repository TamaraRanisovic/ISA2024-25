package com.developer.onlybuns.service.impl;

import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.stereotype.Service;
import com.developer.onlybuns.service.UsernameValidationService;

import java.nio.charset.StandardCharsets;

@Service
public class UsernameValidationServiceImpl implements UsernameValidationService {

    private final BloomFilter<String> bloomFilter;

    private final RegistrovaniKorisnikService registrovaniKorisnikService;

    public UsernameValidationServiceImpl(RegistrovaniKorisnikService registrovaniKorisnikService) {
        this.bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(StandardCharsets.UTF_8),
                15, // expected insertions
                0.01   // 1% false positive probability
        );
        this.registrovaniKorisnikService = registrovaniKorisnikService;
    }

    @Override
    public void addUsername(String username) {
        bloomFilter.put(username);
    }

    @Override
    public boolean mightContainUsername(String username) {
        return bloomFilter.mightContain(username);
    }

    @Override
    public boolean isUsernameValid(String username) {
        if (mightContainUsername(username)) {
            // Double-check in the database to avoid false positives
            return registrovaniKorisnikService.usernameExists(username);
        }
        return false; // Username is definitely not in the Bloom Filter
    }

    @Override
    public void loadUsernamesFromDatabase() {
        for (String username : registrovaniKorisnikService.getAllUsernames()) {
            addUsername(username);
        }
    }
}
