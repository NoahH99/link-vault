package com.noahhendrickson.shorten.url;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class ShortenedURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "short_code", nullable = false, length = 30)
    private String shortCode;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_accessed_date")
    private LocalDateTime lastAccessedDate;

    @Column(name = "clicks", nullable = false)
    private long clicks;

    public ShortenedURL() {
        this.createdDate = LocalDateTime.now();
        this.clicks = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(@NonNull String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(@NonNull String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@Nullable LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(@NonNull LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(@Nullable LocalDateTime lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public void setLastAccessedDateToNow() {
        setLastAccessedDate(LocalDateTime.now());
    }

    public void incrementClicksByOne() {
        setClicks(clicks + 1L);
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDateTime.now());
    }
}
