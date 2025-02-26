package com.example.UrlShortener.service;

import com.example.UrlShortener.constants.UrlConstants;
import com.example.UrlShortener.entity.UrlShortener;
import com.example.UrlShortener.repository.UrlShortenerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
public class UrlShortenerService {
    private final UrlShortenerRepository urlShortenerRepository;
    private EntityManager entityManager;
    private DbLookupService dbLookupService;

    @Autowired
    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository, EntityManager entityManager, DbLookupService dbLookupService) {
        this.urlShortenerRepository = urlShortenerRepository;
        this.entityManager = entityManager;
        this.dbLookupService = dbLookupService;
    }

    private String generateShortKey() {
        Random rand = new SecureRandom();
        int keyLength = 6;
        // Using six as the basic length of the short url
        StringBuilder shortUrl = new StringBuilder(keyLength);
        for (int i = 0; i < keyLength; i++) {
            int randomIndex = rand.nextInt(UrlConstants.CHAR_SET.length());
            shortUrl.append(UrlConstants.CHAR_SET.charAt(randomIndex));
        }
        return shortUrl.toString();
    }

    public ResponseEntity<UrlShortener> shortenUrl(String url) {
        String shortKey = generateShortKey();

        UrlShortener shortUrl = new UrlShortener();
        shortUrl.setShortUrl(shortKey);
        shortUrl.setUrl(url);
        UrlShortener shortened = urlShortenerRepository.save(shortUrl);

        return ResponseEntity.ok(shortened);
    }

    public ResponseEntity<String> getUrlFromShort(String shortUrl) {
        String url = dbLookupService.findUrlByShorName(shortUrl);
        if (url == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(url);
    }
}
