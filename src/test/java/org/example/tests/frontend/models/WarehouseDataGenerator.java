package org.example.tests.frontend.models;

import java.util.Random;
import java.util.stream.IntStream;

public class WarehouseDataGenerator {
    private static final Random random = new Random();

    public static String generateLocation() {
        return generateRandomAlphanumericString(1, 255);
    }

    public static String generateManagerName() {
        return generateRandomAlphaString(1, 100);
    }

    public static Integer generateCapacity() {
        return random.nextInt(100);
    }

    public static Integer generateCurrentStock() {
        return random.nextInt(100);
    }

    public static String generateContactNumber() {
        return "+" + generateRandomNumericString(3, 14);
    }

    public static String generateEmail() {
        return generateRandomAlphaString(17, 20) + "@" + generateRandomAlphaString(4, 6) + "." + generateRandomAlphaString(2, 3);
    }

    public static Boolean generateIsActive() {
        return random.nextBoolean();
    }

    public static String generateAreaSize() {
        double area = Math.round((random.nextDouble() * 9999.99) * 100) / 100.0;
        return String.format("%.2f", area);
    }

    private static String generateRandomAlphanumericString(int minLength, int maxLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder randomString = new StringBuilder(length);

        IntStream.range(0, length).forEach(i -> {
            char randomChar = chars.charAt(random.nextInt(chars.length()));
            randomString.append(randomChar);
        });
        return randomString.toString();
    }

    private static String generateRandomAlphaString(int minLength, int maxLength) {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder randomString = new StringBuilder(length);

        IntStream.range(0, length).forEach(i -> {
            char randomAlpha = alpha.charAt(random.nextInt(alpha.length()));
            randomString.append(randomAlpha);
        });
        return randomString.toString();
    }

    private static String generateRandomNumericString(int minLength, int maxLength) {
        String numeric = "0123456789";
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder randomString = new StringBuilder(length);

        IntStream.range(0, length).forEach(i -> {
            char randomNumeric = numeric.charAt(random.nextInt(numeric.length()));
            randomString.append(randomNumeric);
        });
        return randomString.toString();
    }


}

