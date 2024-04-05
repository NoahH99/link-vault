package com.noahhendrickson.shorten.url;

import java.util.Random;

public class ShortCodeGenerator {

    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final Random random;

    public ShortCodeGenerator() {
        this.random = new Random();
    }

    public String generateRandomString(int length) {
        if (length <= 0) throw new IllegalArgumentException("Must supply a length greater than 0.");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC_CHARACTERS.length());
            sb.append(ALPHANUMERIC_CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public String generateRandomFiveCharacaterString() {
        return generateRandomString(5);
    }
}
