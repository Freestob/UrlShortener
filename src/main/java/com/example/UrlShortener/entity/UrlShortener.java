package com.example.UrlShortener.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "urlShortener")
public class UrlShortener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String shortUrl;

    // getter ans setters

    // Getters
    public Long getId() {return id;}

    public String getUrl() {return url;}

    public String getShortUrl() {return shortUrl;}

    // Setters

    public void setId(Long id) {this.id = id;}

    public void setUrl(String url) {this.url = url;}

    public void setShortUrl(String shortUrl) {this.shortUrl = shortUrl;}
}
