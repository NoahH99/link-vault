package com.noahhendrickson.backend.url;

import java.time.LocalDateTime;

public class URLShortenerDTO {

    private final String originalUrl;
    private final String shortCode;
    private final LocalDateTime expirationDate;

    public URLShortenerDTO(String originalUrl, String shortCode, LocalDateTime expirationDate) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.expirationDate = expirationDate;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
