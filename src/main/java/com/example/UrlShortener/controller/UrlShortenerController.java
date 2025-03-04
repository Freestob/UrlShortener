package com.example.UrlShortener.controller;

import com.example.UrlShortener.entity.ShortenRequestBody;
import com.example.UrlShortener.entity.UrlShortener;
import com.example.UrlShortener.exception.ErrorResponse;
import com.example.UrlShortener.exception.ShortUrlEmptyException;
import com.example.UrlShortener.exception.UrlNotFoundException;
import com.example.UrlShortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shorten")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/url")
    public ResponseEntity<UrlShortener> shortenUrl(@RequestBody ShortenRequestBody request) {
        return urlShortenerService.shortenUrl(request.getOriginalUrl());
    }
    @GetMapping("/url")
    public ResponseEntity<String> getUrl(@RequestParam("shortUrl") String shortUrl)
            throws UrlNotFoundException, ShortUrlEmptyException {
        if (shortUrl == null || shortUrl.isEmpty()) {
            throw new ShortUrlEmptyException("Short Url cannot be empty.");
        }
        return urlShortenerService.getUrlFromShort(shortUrl);
    }

    @ExceptionHandler(value = ShortUrlEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleShortUrlEmptyException(ShortUrlEmptyException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
