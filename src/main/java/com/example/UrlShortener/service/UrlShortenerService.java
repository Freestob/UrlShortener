package com.example.UrlShortener.service;

import com.example.UrlShortener.constants.UrlConstants;
import com.example.UrlShortener.entity.UrlShortener;
import com.example.UrlShortener.exception.UrlNotFoundException;
import com.example.UrlShortener.repository.UrlShortenerRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;


@Service
public class UrlShortenerService {
    private final UrlShortenerRepository urlShortenerRepository;
    private final DbLookupService dbLookupService;

    @Autowired
    public UrlShortenerService(UrlShortenerRepository urlShortenerRepository, DbLookupService dbLookupService) {
        this.urlShortenerRepository = urlShortenerRepository;
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

    public ResponseEntity<UrlShortener> shortenUrl(String url) throws DataAccessException {
        String shortKey = generateShortKey();

        UrlShortener shortUrl = new UrlShortener();
        shortUrl.setShortUrl(shortKey);
        shortUrl.setUrl(url);

        try {
            UrlShortener shortened = urlShortenerRepository.save(shortUrl);
            return ResponseEntity.ok(shortened);
        } catch (Exception e){
            throw new DataAccessException(String.format("Error saving %s in database", url)) {};
        }
    }

    public ResponseEntity<String> getUrlFromShort(String shortUrl) throws UrlNotFoundException {
        String url = dbLookupService.findUrlByShortName(shortUrl);
        if (url == null) {
            throw new UrlNotFoundException("Url not found");
        }
        return ResponseEntity.ok(url);
    }
}
