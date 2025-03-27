package org.example.tests.frontend.models;

import java.util.Random;
import java.util.stream.IntStream;

public class ProductDataGenerator {
    private static final Random random = new Random();
    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
    }
    public static String generateDescription() {
        return generateRandomAlphanumericString(10, 500);
    }
    public static String generateCategory() {
        return generateRandomAlphanumericString(3, 50);
    }
    public static String generatePrice() {
        double price = Math.round((random.nextDouble() * 99999999.99) * 100) / 100.0;
        return String.format("%.2f", price);
    }
    public static int generateStockQuantity() {
        return random.nextInt(1000);
    }
    public static String generateImageUrl() {
        String[] formats = {".png", ".jpeg", ".jpg"};
        String randomFormat = formats[random.nextInt(formats.length)];
        return "http://example.com/product/image" + generateRandomAlphanumericString(5, 20) + randomFormat;
    }
    public static String generateWeight() {
        double weight = Math.round(random.nextDouble() * 100.0 * 100) / 100.0;
        return String.format("%.2f", weight);
    }
    public static String generateDimensions() {
        return random.nextInt(5) + "x" + random.nextInt(5) + "x" + random.nextInt(5);
    }
    public static String generateManufacturer() {
        return generateRandomAlphanumericString(3, 100);
    }
    private static String generateRandomAlphanumericString(int minLength, int maxLength) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder stringBuilder = new StringBuilder(length);
        IntStream.range(0, length).forEach(i -> {
            char charsRandom = chars.charAt(random.nextInt(chars.length()));
            stringBuilder.append(charsRandom);
        });
        return stringBuilder.toString();
    }
}
