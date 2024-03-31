package com.noahhendrickson.backend.url;

import java.time.LocalDateTime;

public class ShortenedURLBuilder {

    private String originalUrl;
    private String shortCode;
    private LocalDateTime expirationDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastAccessedDate;
    private int clicks;

    public ShortenedURLBuilder() {
        this.createdDate = LocalDateTime.now();
        this.clicks = 0;
    }

    public ShortenedURLBuilder originalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public ShortenedURLBuilder shortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public ShortenedURLBuilder expirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ShortenedURLBuilder createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public ShortenedURLBuilder lastAccessedDate(LocalDateTime lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
        return this;
    }

    public ShortenedURLBuilder clicks(int clicks) {
        this.clicks = clicks;
        return this;
    }

    public ShortenedURL build() {
        ShortenedURL url = new ShortenedURL();

        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);
        url.setExpirationDate(expirationDate);
        url.setCreatedDate(createdDate);
        url.setLastAccessedDate(lastAccessedDate);
        url.setClicks(clicks);

        return url;
    }
}
