package com.example.UrlShortener.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "urlShortener")
public class UrlShortener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String url;

    @Setter
    @Getter
    @Column(nullable = false)
    private String shortUrl;

    // getter ans setters

    // Getters
    public Long getId() {return id;}

    // Setters

    public void setId(Long id) {this.id = id;}

}
