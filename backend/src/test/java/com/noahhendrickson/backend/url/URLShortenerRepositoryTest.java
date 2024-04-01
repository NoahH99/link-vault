package com.noahhendrickson.backend.url;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class URLShortenerRepositoryTest {

    @Autowired
    private URLShortenerRepository repository;

    @Test
    public void givenAShortCodeThatExitsInTheDatabase_whenFindShortenedURLByShortCode_thenTheReturnedURLIsNotNull() {
        String shortCode = "abc";

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode(shortCode)
                        .build()
        );

        ShortenedURL url = repository.findShortenedURLByShortCode(shortCode);
        assertNotNull(url);
    }

    @Test
    public void givenAShortCodeThatExitsInTheDatabase_whenFindShortenedURLByShortCode_thenTheReturnedURLShortCodeIsTheSameAsExpected() {
        String shortCode = "abc";

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode(shortCode)
                        .build()
        );

        ShortenedURL url = repository.findShortenedURLByShortCode(shortCode);
        assertEquals(shortCode, url.getShortCode());
    }

    @Test
    public void givenAShortCodeThatDoesNotExitsInTheDatabase_whenFindShortenedURLByShortCode_thenTheReturnedURLSShouldBeNull() {
        String shortCode = "non-existent";
        ShortenedURL url = repository.findShortenedURLByShortCode(shortCode);
        assertNull(url);
    }

    @Test
    public void givenANullShortCode_whenFindShortenedURLByShortCode_thenTheReturnedURLSShouldBeNull() {
        ShortenedURL url = repository.findShortenedURLByShortCode(null);
        assertNull(url);
    }

    @Test
    public void givenAShortCodeThatExitsInTheDatabase_whenExistsShortenedURLByShortCode_thenTheReturnedBooleanShouldBeTrue() {
        String shortCode = "abc";

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode(shortCode)
                        .build()
        );

        boolean exists = repository.existsShortenedURLByShortCode(shortCode);
        assertTrue(exists);
    }

    @Test
    public void givenAShortCodeThatDoesNotExitsInTheDatabase_whenExistsShortenedURLByShortCode_thenTheReturnedBooleanShouldBeFalse() {
        String shortCode = "non-existent";
        boolean exists = repository.existsShortenedURLByShortCode(shortCode);
        assertFalse(exists);
    }

    @Test
    public void givenANullShortCode_whenExistsShortenedURLByShortCode_thenTheReturnedBooleanShouldBeFalse() {
        boolean exists = repository.existsShortenedURLByShortCode(null);
        assertFalse(exists);
    }

    @Test
    public void givenNoEntriesInTheDatabase_whenSumClicks_thenTheReturnedValueShouldBeZero() {
        long numberOfClicks = repository.sumClicks();
        assertEquals(0L, numberOfClicks);
    }

    @Test
    public void givenFiveEntriesInTheDatabaseWithTotalClicksEqualingTwentyFive_whenSumClicks_thenTheReturnedValueShouldBeTwentyFive() {
        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(5)
                        .build()
        );

        long numberOfClicks = repository.sumClicks();
        assertEquals(25L, numberOfClicks);
    }

    @Test
    public void givenNoEntriesInTheDatabase_whenCountShortenedURLs_thenTheReturnedValueShouldBeZero() {
        long numberOfUrls = repository.countShortenedURLs();
        assertEquals(0L, numberOfUrls);
    }

    @Test
    public void givenFiveEntriesInTheDatabase_whenCountShortenedURLs_thenTheReturnedValueShouldBeFive() {
        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(5)
                        .build()
        );

        long numberOfUrls = repository.countShortenedURLs();
        assertEquals(5L, numberOfUrls);
    }

    @Test
    public void givenNoEntriesInTheDatabase_whenFindTop10URLsByOrderByClicksDesc_thenTheReturnedValueShouldBeZero() {
        List<ShortenedURL> numberOfUrls = repository.findTop10URLsByOrderByClicksDesc();
        assertEquals(0L, numberOfUrls.size());
    }

    @Test
    public void givenFiveEntriesInTheDatabase_whenFindTop10URLsByOrderByClicksDesc_thenTheReturnedFiveValueShouldBeInDescendingOrder() {
        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(5)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(85)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(0)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(100)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(30)
                        .build()
        );

        List<ShortenedURL> numberOfUrls = repository.findTop10URLsByOrderByClicksDesc();
        assertEquals(5L, numberOfUrls.size());

        for (int i = 0; i < numberOfUrls.size() - 1; i++) {
            assertTrue(numberOfUrls.get(i).getClicks() >= numberOfUrls.get(i + 1).getClicks());
        }
    }

    @Test
    public void givenTwentyEntriesInTheDatabase_whenFindTop10URLsByOrderByClicksDesc_thenTheReturnedTenValueShouldBeInDescendingOrder() {
        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(1000)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(900)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(800)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(700)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(600)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(500)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(400)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(300)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(200)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(100)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(90)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(80)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(70)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(60)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(50)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("abc")
                        .clicks(40)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("def")
                        .clicks(30)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("ghi")
                        .clicks(20)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("jkl")
                        .clicks(10)
                        .build()
        );

        repository.save(
                new ShortenedURLBuilder()
                        .originalUrl("https://www.example.com")
                        .shortCode("mno")
                        .clicks(0)
                        .build()
        );

        List<ShortenedURL> numberOfUrls = repository.findTop10URLsByOrderByClicksDesc();
        assertEquals(10L, numberOfUrls.size());

        for (int i = 0; i < numberOfUrls.size() - 1; i++) {
            assertTrue(numberOfUrls.get(i).getClicks() >= 100);
            assertTrue(numberOfUrls.get(i).getClicks() >= numberOfUrls.get(i + 1).getClicks());
        }
    }
}