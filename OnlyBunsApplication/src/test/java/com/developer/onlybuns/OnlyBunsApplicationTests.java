package com.developer.onlybuns;

import com.developer.onlybuns.controller.RegistrovaniKorisnikController;
import com.developer.onlybuns.entity.*;
import com.developer.onlybuns.enums.Uloga;
import com.developer.onlybuns.repository.KorisnikRepository;
import com.developer.onlybuns.repository.RegistrovaniKorisnikRepository;
import com.developer.onlybuns.service.RegistrovaniKorisnikService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class OnlyBunsApplicationTests {

	@Autowired
	private RegistrovaniKorisnikService registrovaniKorisnikService;

	@Autowired
	private RegistrovaniKorisnikRepository registrovaniKorisnikRepository;

	@Autowired
	private RegistrovaniKorisnikController registrovaniKorisnikController;

	@Test
	@Transactional
	void testConcurrentRegistration() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(1);

		List<Objava> objave = new ArrayList<>();
		List<Komentar> komentari = new ArrayList<>();
		List<Pratioci> followers = new ArrayList<>();
		List<Pratioci> following = new ArrayList<>();

		Lokacija lokacija1 = new Lokacija("Bulevar Oslobodjenja 120", "Novi Sad", "Srbija");
		Lokacija lokacija2 = new Lokacija("Bulevar Oslobodjenja 50", "Novi Sad", "Srbija");

		RegistrovaniKorisnik korisnik1 = new RegistrovaniKorisnik(
				"testUser", "test@gmail.com", "test", "test name", "test lastname", "1234567890",
				Uloga.REGISTROVANI_KORISNIK, false, lokacija1, objave, komentari, following, followers
		);

		RegistrovaniKorisnik korisnik2 = new RegistrovaniKorisnik(
				"testUser", "test2@gmail.com", "test2", "test name2", "test lastname2", "1234567800",
				Uloga.REGISTROVANI_KORISNIK, false, lokacija2, objave, komentari, following, followers
		);

		Runnable task1 = () -> {
			try {
				latch.await();
				registrovaniKorisnikController.register(korisnik1);
				System.out.println("Task 1: User registered successfully.");
			} catch (Exception e) {
				System.out.println("Task 1: " + e.getMessage());
			}
		};

		Runnable task2 = () -> {
			try {
				latch.await();
				registrovaniKorisnikController.register(korisnik2);
				System.out.println("Task 2: User registered successfully.");
			} catch (Exception e) {
				System.out.println("Task 2: " + e.getMessage());
			}
		};

		executor.submit(task1);
		executor.submit(task2);

		latch.countDown();

		executor.shutdown();
		executor.awaitTermination(20, TimeUnit.SECONDS);

		Integer userCount = registrovaniKorisnikRepository.countByKorisnickoIme("testUser");
		System.out.println("User count with username 'testUser': " + userCount);

		assertEquals(1, userCount, "Expected exactly one user to be saved.");
	}

}
