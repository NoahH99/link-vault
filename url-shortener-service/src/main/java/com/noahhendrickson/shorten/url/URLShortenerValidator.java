package com.noahhendrickson.shorten.url;

import com.noahhendrickson.shorten.exception.InvalidRequestException;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLShortenerValidator {

    private final URLShortenerRepository repository;

    public URLShortenerValidator(URLShortenerRepository repository) {
        this.repository = repository;
    }

    public String validate(URLShortenerDTO url) throws InvalidRequestException {
        if (url == null) throw new InvalidRequestException("Request body cannot be null.");

        String originalUrl = url.getOriginalUrl();
        if (originalUrl == null) throw new InvalidRequestException("originalUrl cannot be null.");
        if (originalUrl.isBlank()) throw new InvalidRequestException("originalUrl cannot be blank.");
        if (!isValidURL(originalUrl)) throw new InvalidRequestException("originalUrl must be a valid url.");

        String shortCode = url.getShortCode();
        if (shortCode == null) shortCode = generateShortCode();
        else if (repository.existsShortenedURLByShortCode(shortCode))
            throw new InvalidRequestException("shortCode '" + shortCode + "' already exists.");
        if (shortCode.isBlank()) throw new InvalidRequestException("shortCode cannot be blank.");
        if (shortCode.length() > 30) throw new InvalidRequestException("hortCode cannot exceed 30 characters.");

        LocalDateTime expirationDate = url.getExpirationDate();
        if (expirationDate != null && expirationDate.isBefore(LocalDateTime.now())) {
            throw new InvalidRequestException("expirationDate cannot be in the past.");
        }

        return shortCode;
    }

    private String generateShortCode() {
        ShortCodeGenerator generator = new ShortCodeGenerator();
        String code = generator.generateRandomFiveCharacaterString();

        while (repository.existsShortenedURLByShortCode(code)) {
            code = generator.generateRandomFiveCharacaterString();
        }

        return code;
    }

    private boolean isValidURL(String url) {
        String regex = "^https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&//=]*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}
