package org.example.tests.frontend.models;

import java.util.Random;

public class SupplierDataGenerator {
    private static final Random random = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";

    private static String generateRandomString(String characters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String generateName() {
        return generateRandomString(ALPHABET, 10) + " Ltd"; // Ensuring length between 3 and 100
    }

    public static String generateContactName() {
        return generateRandomString(ALPHABET, 10); // Max length 100
    }

    public static String generateContactEmail() {
        return generateRandomString(ALPHABET, 8) + "@test.com"; // Max length 20
    }

    public static String generatePhoneNumber() {
        return "+" + generateRandomString(NUMERIC, random.nextInt(10) + 5); // Between 5-15 chars
    }

    public static String generateAddress() {
        return generateRandomString(ALPHABET + NUMERIC + " ", 20); // Max 200, simplified
    }

    public static String generateCountry() {
        return generateRandomString(ALPHABET, 7); // Max 50, only letters
    }

    public static String generateCity() {
        return generateRandomString(ALPHABET, 7); // Max 50, only letters
    }

    public static String generateWebsite() {
        return "https://www." + generateRandomString(ALPHABET.toLowerCase(), 5) + ".com";
    }
}
