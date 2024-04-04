package com.noahhendrickson.shorten.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/url-shortener")
public class URLShortenerController {

    private final URLShortenerService shortenerService;

    @Autowired
    public URLShortenerController(URLShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<URLShortenerDTO> shortenURL(@RequestBody URLShortenerDTO request) {
        return shortenerService.shortenURL(request);
    }

    @GetMapping("/s/{shortCode}")
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
