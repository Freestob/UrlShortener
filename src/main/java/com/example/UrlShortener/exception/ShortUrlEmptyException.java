package com.example.UrlShortener.exception;

public class ShortUrlEmptyException extends Exception {

    public ShortUrlEmptyException() {}
    public ShortUrlEmptyException(String message) {
        super(message);
    }
}