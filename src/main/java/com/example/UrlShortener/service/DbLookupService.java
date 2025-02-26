package com.example.UrlShortener.service;

import com.example.UrlShortener.entity.UrlShortener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbLookupService {

    @PersistenceContext
    private EntityManager em;

    public String findUrlByShorName(String shortUrl) {
        Query query = em.createQuery("SELECT c FROM UrlShortener c WHERE c.shortUrl = :shortUrl");
        query.setParameter("shortUrl", shortUrl);

       UrlShortener urlShortener = (UrlShortener) query.getResultList().get(0);
       return urlShortener.getUrl();


    }
}
