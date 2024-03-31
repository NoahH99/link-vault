package com.noahhendrickson.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class URL {

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
    private int clicks;

    public URL() {
        this.createdDate = LocalDateTime.now();
        this.clicks = 0;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(LocalDateTime lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
}
