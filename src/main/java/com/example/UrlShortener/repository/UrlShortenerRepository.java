package com.example.UrlShortener.repository;

import com.example.UrlShortener.entity.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {
}
