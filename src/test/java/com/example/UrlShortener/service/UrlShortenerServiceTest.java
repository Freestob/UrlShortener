package com.example.UrlShortener.service;

import com.example.UrlShortener.entity.UrlShortener;
import com.example.UrlShortener.exception.UrlNotFoundException;
import com.example.UrlShortener.repository.UrlShortenerRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlShortenerServiceTest {


    @InjectMocks
    private UrlShortenerService urlShortenerServiceMock;

    @Mock
    private UrlShortenerRepository urlShortenerRepositoryMock;

    @Mock
    private DbLookupService dbLookupServiceMock;


    @Test
    void getUrlFromShort_ReturnsCorrectUrl() throws UrlNotFoundException {

        // Arrange
        String url = "https://www.google.com";
        when(dbLookupServiceMock.findUrlByShortName("BIxa9o")).thenReturn(url);

        // Act
        ResponseEntity<String> result = urlShortenerServiceMock.getUrlFromShort("BIxa9o");
        String resultBody = result.getBody();

        // Assert
        assertNotNull(result);
        assertEquals(url, resultBody);
        verify(dbLookupServiceMock).findUrlByShortName("BIxa9o");
    }

    @Test
    void getUrlFromShort_ThrowsExceptionWhenUrlNotFound()   {

        UrlNotFoundException ex = assertThrows(UrlNotFoundException.class, () -> {
            urlShortenerServiceMock.getUrlFromShort("BIxa9o");
        });
        assertNotNull(ex);
        assertTrue(ex.getMessage().contains("Url not found"));
    }

    @Test
    void shortenUrl_Ok() throws DataAccessException {
        String url = "https://www.google.com";

        UrlShortener shortened = new UrlShortener();
        shortened.setId(1L);
        shortened.setUrl(url);
        shortened.setShortUrl("BIxa9o"); // Example short URL

        when(urlShortenerRepositoryMock.save(any(UrlShortener.class))).thenReturn(shortened);

        ResponseEntity<UrlShortener> result = urlShortenerServiceMock.shortenUrl(url);
        UrlShortener resultBody = result.getBody();

        assertNotNull(resultBody);
        assertEquals(6, resultBody.getShortUrl().length());
        verify(urlShortenerRepositoryMock, times(1)).save(any(UrlShortener.class));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void shortenUrl_DataAccessException() {
        String url = "https://www.google.com";

        when(urlShortenerRepositoryMock.save(any(UrlShortener.class))).thenThrow(EmptyResultDataAccessException.class);

        DataAccessException ex = assertThrows(DataAccessException.class, () -> {
            urlShortenerServiceMock.shortenUrl(url);
        });
        assertNotNull(ex);
        assertTrue(ex.getMessage().contains(String.format("Error saving %s in database", url)));
    }
}