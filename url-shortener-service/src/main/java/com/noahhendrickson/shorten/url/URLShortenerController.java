package com.noahhendrickson.shorten.url;

import com.noahhendrickson.shorten.exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/url")
public class URLShortenerController {

    private final URLShortenerService shortenerService;

    @Autowired
    public URLShortenerController(URLShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<URLShortenerDTO> shortenURL(@RequestBody URLShortenerDTO request) {
        try {
            return shortenerService.shortenURL(request);
        } catch (HttpMessageNotReadableException exception) {
            throw new InvalidRequestException(exception.getMessage());
        }
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectURL(@PathVariable String shortCode) {
        return shortenerService.redirectURL(shortCode);
    }

    @GetMapping("/analytics")
    public ResponseEntity<GlobalAnalyticsDTO> getGlobalAnalytics() {
        return shortenerService.getGlobalAnalytics();
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<AnalyticsDTO> getAnalyticsForURL(@PathVariable String shortCode) {
        return shortenerService.getAnalyticsForURL(shortCode);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Integer>> healthCheck() {
        return ResponseEntity.ok(Map.of("status", 200));
    }
}
