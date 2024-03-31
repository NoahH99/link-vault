package com.noahhendrickson.backend.services;

import com.noahhendrickson.backend.builders.URLBuilder;
import com.noahhendrickson.backend.models.URL;
import com.noahhendrickson.backend.reponses.ShortenedURLResponse;
import com.noahhendrickson.backend.repositories.URLRepository;
import com.noahhendrickson.backend.requests.ShortenedURLRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class URLService {

    public static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Autowired
    private URLRepository urlRepository;

    private final Random random;

    public URLService() {
        this.random = new Random();
    }

    public ShortenedURLResponse shortenURL(String originalURL, String shortCode, LocalDateTime expirationDate) {
        URL url = new URLBuilder()
                .setOriginalUrl(originalURL)
                .setShortCode(shortCode)
                .setExpirationDate(expirationDate)
                .build();

        save(url);

        return new ShortenedURLResponse(originalURL, shortCode, shortCode, expirationDate);
    }

    public URL getByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    public URL save(URL url) {
        return urlRepository.save(url);
    }

    public String getShortCode(ShortenedURLRequest request) {
        String shortCode;

        if (request.getShortCode() == null || request.getShortCode().isBlank()) shortCode = generateShortCode();
        else shortCode = request.getShortCode();

        while (shortCodeExists(shortCode)) {
            shortCode = generateShortCode();
        }

        return shortCode;
    }

    private boolean shortCodeExists(String shortCode) {
        return getByShortCode(shortCode) != null;
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            shortCode.append(ALPHANUMERIC_CHARACTERS.charAt(random.nextInt(ALPHANUMERIC_CHARACTERS.length())));
        }

        return shortCode.toString();
    }

    public LocalDateTime parseExpirationDate(ShortenedURLRequest request) {
        if (request.getExpirationDate() == null) return null;

        return LocalDateTime.parse(request.getExpirationDate());
    }

    public void updateURLStats(URL url) {
        url.setLastAccessedDate(LocalDateTime.now());
        url.setClicks(url.getClicks() + 1);
        save(url);
    }

    public HttpHeaders createRedirectHeaders(String originalUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return headers;
    }
}
