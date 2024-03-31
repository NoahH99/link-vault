package com.noahhendrickson.backend.controllers;

import com.noahhendrickson.backend.models.URL;
import com.noahhendrickson.backend.reponses.ShortenedURLResponse;
import com.noahhendrickson.backend.requests.ShortenedURLRequest;
import com.noahhendrickson.backend.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class URLController {

    @Autowired
    private URLService urlService;

    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenedURLResponse> shortenURL(@RequestBody ShortenedURLRequest request) {
        String originalURL = request.getOriginalURL();
        String shortCode = urlService.getShortCode(request);
        LocalDateTime expirationDate = urlService.parseExpirationDate(request);

        ShortenedURLResponse shortenedURLResponse = urlService.shortenURL(originalURL, shortCode, expirationDate);

        return new ResponseEntity<>(shortenedURLResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity redirectShortURL(@PathVariable String shortCode) {
        URL url = urlService.getByShortCode(shortCode);

        if (url == null) return ResponseEntity.notFound().build();

        urlService.updateURLStats(url);
        HttpHeaders headers = urlService.createRedirectHeaders(url.getOriginalUrl());

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }
}
