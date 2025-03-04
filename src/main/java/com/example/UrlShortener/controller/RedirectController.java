package com.example.UrlShortener.controller;

import com.example.UrlShortener.exception.ErrorResponse;
import com.example.UrlShortener.exception.UrlNotFoundException;
import com.example.UrlShortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/redirect")
public class RedirectController {
    private final UrlShortenerService urlShortenerService;

    @Autowired
    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> redirect(@RequestParam String shortUrl) throws UrlNotFoundException {
        String url = urlShortenerService.getUrlFromShort(shortUrl).getBody();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @ExceptionHandler(value = UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleUrlNotFoundException(UrlNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
