package com.noahhendrickson.backend.builders;

import com.noahhendrickson.backend.models.URL;

import java.time.LocalDateTime;

public class URLBuilder {

    private String originalUrl;
    private String shortCode;
    private LocalDateTime expirationDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastAccessedDate;
    private int clicks;

    public URLBuilder() {
        this.createdDate = LocalDateTime.now();
        this.clicks = 0;
    }

    public URLBuilder setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public URLBuilder setShortCode(String shortCode) {
        this.shortCode = shortCode;
        return this;
    }

    public URLBuilder setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public URLBuilder setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public URLBuilder setLastAccessedDate(LocalDateTime lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
        return this;
    }

    public URLBuilder setClicks(int clicks) {
        this.clicks = clicks;
        return this;
    }

    public URL build() {
        URL url = new URL();

        url.setOriginalUrl(this.originalUrl);
        url.setShortCode(this.shortCode);
        url.setExpirationDate(this.expirationDate);
        url.setCreatedDate(this.createdDate);
        url.setLastAccessedDate(this.lastAccessedDate);
        url.setClicks(this.clicks);

        return url;
    }
}
