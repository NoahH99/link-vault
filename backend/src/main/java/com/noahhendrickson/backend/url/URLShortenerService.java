package com.noahhendrickson.backend.url;

import com.noahhendrickson.backend.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class URLShortenerService {

    private final URLShortenerRepository shortenerRepository;

    @Autowired
    public URLShortenerService(URLShortenerRepository shortenerRepository) {
        this.shortenerRepository = shortenerRepository;
    }

    public ResponseEntity<URLShortenerDTO> shortenURL(URLShortenerDTO request) {
        String shortCode = new URLShortenerValidator(shortenerRepository).validate(request);
        ShortenedURL url = buildShortenedURL(request, shortCode);
        shortenerRepository.save(url);

        return new ResponseEntity<>(new URLShortenerDTO(
                url.getOriginalUrl(),
                url.getShortCode(),
                url.getExpirationDate()
        ), HttpStatus.CREATED);
    }

    public ResponseEntity<?> redirectURL(String shortCode) {
        ShortenedURL shortenedURL = shortenerRepository.findShortenedURLByShortCode(shortCode);

        if (isShortenedURLNotFound(shortenedURL)) return ResponseEntity.notFound().build();

        updateStatistics(shortenedURL);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", shortenedURL.getOriginalUrl())
                .build();
    }

    public ResponseEntity<GlobalAnalyticsDTO> getGlobalAnalytics() {
        return new ResponseEntity<>(
                new GlobalAnalyticsDTO(
                        shortenerRepository.countShortenedURLs(),
                        shortenerRepository.sumClicks(),
                        shortenerRepository.findTop10URLsByOrderByClicksDesc()
                ),
                HttpStatus.OK
        );
    }

    public ResponseEntity<AnalyticsDTO> getAnalyticsForURL(String shortCode) {
        ShortenedURL url = shortenerRepository.findShortenedURLByShortCode(shortCode);

        if (url == null) throw new InvalidRequestException("Invalid short code.");

        return new ResponseEntity<>(
                new AnalyticsDTO(
                        url.getOriginalUrl(),
                        url.getShortCode(),
                        url.getExpirationDate(),
                        url.getCreatedDate(),
                        url.getLastAccessedDate(),
                        url.getClicks()
                ),
                HttpStatus.OK
        );
    }

    private ShortenedURL buildShortenedURL(URLShortenerDTO request, String shortCode) {
        return new ShortenedURLBuilder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(shortCode)
                .expirationDate(request.getExpirationDate())
                .build();
    }

    private boolean isShortenedURLNotFound(ShortenedURL shortenedURL) {
        return shortenedURL == null || shortenedURL.getOriginalUrl() == null;
    }

    private void updateStatistics(ShortenedURL url) {
        url.setLastAccessedDateToNow();
        url.incrementClicksByOne();
        shortenerRepository.save(url);
    }
}
