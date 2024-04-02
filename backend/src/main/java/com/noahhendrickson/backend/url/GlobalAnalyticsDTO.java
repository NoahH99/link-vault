package com.noahhendrickson.backend.url;

import java.util.List;

public class GlobalAnalyticsDTO {

    private final long totalShortenedURLs;
    private final long totalClicks;
    private final List<AnalyticsDTO> topClickedURLs;

    public GlobalAnalyticsDTO(long totalShortenedURLs, long totalClicks, List<ShortenedURL> topClickedURLs) {
        this.totalShortenedURLs = totalShortenedURLs;
        this.totalClicks = totalClicks;
        this.topClickedURLs = topClickedURLs.stream()
                .map(url -> new AnalyticsDTO(
                        url.getOriginalUrl(),
                        url.getShortCode(),
                        url.getExpirationDate(),
                        url.getCreatedDate(),
                        url.getLastAccessedDate(),
                        url.getClicks())
                ).toList();
    }

    public long getTotalShortenedURLs() {
        return totalShortenedURLs;
    }

    public long getTotalClicks() {
        return totalClicks;
    }

    public List<AnalyticsDTO> getTopClickedURLs() {
        return topClickedURLs;
    }
}
