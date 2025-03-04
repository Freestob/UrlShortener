package com.example.UrlShortener.exception;

public class UrlNotFoundException extends Exception {

    public UrlNotFoundException() {}
    public UrlNotFoundException(String message) {
        super(message);
    }
}