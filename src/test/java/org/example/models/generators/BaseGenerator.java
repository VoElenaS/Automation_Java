package org.example.models.generators;

import java.util.Random;
import java.util.stream.IntStream;

class BaseGenerator {
    private final static String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final Random random = new Random();


    static String generateRandomAlphanumericString(int minLength, int maxLength) {
        return generateStringRandomLength(maxLength, minLength, CHARS);
    }

    static String generateRandomAlphaString(int minLength, int maxLength) {
        return generateStringRandomLength(maxLength, minLength, ALPHABET);
    }

    static String generateRandomNumericString(int minLength, int maxLength) {
        return generateStringRandomLength(maxLength, minLength, NUMERIC);
    }

    static String generateRandomLowerCaseString(int length) {
        return generateRandomString(ALPHABET.toLowerCase(), length);
    }

    static String generateRandomNumeric(int length) {
        return generateRandomString(NUMERIC, length);
    }

    static String generateRandomStringWithNumbers(int length) {
        return generateRandomString(CHARS + " ", length);
    }

    static String generateRandomString(int length) {
        return generateRandomString(ALPHABET, length);
    }

    static double nextDouble() {
        return random.nextDouble();
    }

    static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    private static String generateStringRandomLength(int maxLength, int minLength, String chars) {
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder stringBuilder = new StringBuilder(length);
        IntStream.range(0, length).forEach(i -> {
            char charsRandom = chars.charAt(random.nextInt(chars.length()));
            stringBuilder.append(charsRandom);
        });
        return stringBuilder.toString();
    }

    private static String generateRandomString(String characters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}


