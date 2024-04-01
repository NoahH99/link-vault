package com.noahhendrickson.backend.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface URLShortenerRepository extends JpaRepository<ShortenedURL, Long> {

    ShortenedURL findShortenedURLByShortCode(String shortCode);

    boolean existsShortenedURLByShortCode(String shortCode);

    @Query("SELECT COALESCE(SUM(url.clicks), 0) FROM ShortenedURL url")
    long sumClicks();

    @Query("SELECT COALESCE(COUNT(*), 0) FROM ShortenedURL")
    long countShortenedURLs();

    @Query("SELECT url FROM ShortenedURL url  WHERE url.clicks IN (SELECT url.clicks FROM ShortenedURL url ORDER BY url.clicks DESC LIMIT 10) ORDER BY url.clicks DESC")
    List<ShortenedURL> findTop10URLsByOrderByClicksDesc();
}
