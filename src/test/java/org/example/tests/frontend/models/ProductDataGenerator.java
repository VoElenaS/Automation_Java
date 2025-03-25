package org.example.tests.frontend.models;

import java.util.Random;
import java.util.stream.IntStream;

public class ProductDataGenerator {

    private static final Random random = new Random();

    // Method to generate a valid product name (3-100 characters, only letters and digits)
    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
    }

    // Method to generate description (up to 500 characters, no restrictions on symbols)
    public static String generateDescription() {
        return generateRandomAlphanumericString(10, 500);  // Random string of 10-500 characters
    }

    // Method to generate a valid category (3-50 characters, only letters and numbers)
    public static String generateCategory() {
        return generateRandomAlphanumericString(3, 50);
    }

    // Method to generate price (positive number with 2 decimals, max 10 digits, including decimals)
    public static String generatePrice() {
        double price = Math.round((random.nextDouble() * 99999999.99) * 100) / 100.0;  // Max 10 digits (including decimal part)
        return String.format("%.2f", price);
    }

    // Method to generate stock quantity (whole number, at least 0)
    public static int generateStockQuantity() {
        return random.nextInt(1000);  // Random stock quantity between 0 and 999.
    }

    // Method to generate availability flag (boolean)
    public static boolean generateIsAvailable() {
        return random.nextBoolean();  // Random availability flag.
    }

    // Method to generate a valid image URL (max 255 characters, only png, jpeg, jpg)
    public static String generateImageUrl() {
        String[] formats = {".png", ".jpeg", ".jpg"};
        String randomFormat = formats[random.nextInt(formats.length)];
        return "http://example.com/product/image" + generateRandomAlphanumericString(5, 20) + randomFormat;
    }

    // Method to generate a weight (positive number with a decimal point)
    public static String generateWeight() {
        double weight = Math.round(random.nextDouble() * 100.0 * 100) / 100.0;  // Random weight with 2 decimals
        return String.format("%.2f", weight);
    }

    // Method to generate dimensions (max 100 characters, format "number x number x number")
    public static String generateDimensions() {
        return random.nextInt(5) + "x" + random.nextInt(5) + "x" + random.nextInt(5);
    }

    // Method to generate manufacturer (max 100 characters, only letters and digits)
    public static String generateManufacturer() {
        return generateRandomAlphanumericString(3, 100);
    }

    // Utility method to generate random strings with letters/numbers only or any characters
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

    // Example method to print out the data
    public static void main(String[] args) {
        System.out.println("Name: " + generateName());
        System.out.println("Description: " + generateDescription());
        System.out.println("Category: " + generateCategory());
        System.out.println("Price: " + generatePrice());
        System.out.println("Stock Quantity: " + generateStockQuantity());
        System.out.println("Is Available: " + generateIsAvailable());
        System.out.println("Image URL: " + generateImageUrl());
        System.out.println("Weight: " + generateWeight());
        System.out.println("Dimensions: " + generateDimensions());
        System.out.println("Manufacturer: " + generateManufacturer());
    }
}
