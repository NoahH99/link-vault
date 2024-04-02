package com.noahhendrickson.backend.url;

import java.time.LocalDateTime;

public class AnalyticsDTO extends URLShortenerDTO {

    private final LocalDateTime createdDate;
    private final LocalDateTime lastAccessedDate;
    private final long clicks;

    public AnalyticsDTO(String originalUrl, String shortCode, LocalDateTime expirationDate, LocalDateTime createdDate, LocalDateTime lastAccessedDate, long clicks) {
        super(originalUrl, shortCode, expirationDate);
        this.createdDate = createdDate;
        this.lastAccessedDate = lastAccessedDate;
        this.clicks = clicks;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastAccessedDate() {
        return lastAccessedDate;
    }

    public long getClicks() {
        return clicks;
    }
}
