package com.noahhendrickson.backend.reponses;

import java.time.LocalDateTime;

public class ShortenedURLResponse {

    private String originalUrl;
    private String shortUrl;
    private String shortCode;
    private LocalDateTime expirationDate;

    public ShortenedURLResponse(String originalUrl, String shortUrl, String shortCode, LocalDateTime expirationDate) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.shortCode = shortCode;
        this.expirationDate = expirationDate;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
}
