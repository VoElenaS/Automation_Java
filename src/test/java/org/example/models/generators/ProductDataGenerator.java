package org.example.models.generators;

import org.example.models.request.ProductRequest;

import static org.example.models.generators.BaseGenerator.*;

public class ProductDataGenerator {

    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
    }

    public static String generateDescription() {
        return generateRandomAlphanumericString(10, 50);
    }

    public static String generateCategory() {
        return generateRandomAlphanumericString(3, 50);
    }

    public static String generatePrice() {
        double price = Math.round((nextDouble() * 999999.99) * 100) / 100.0;
        return String.format("%.2f", price);
    }

    public static int generateStockQuantity() {
        return nextInt(1000);
    }

    public static String generateImageUrl() {
        String[] formats = {".png", ".jpeg", ".jpg"};
        String randomFormat = formats[nextInt(formats.length)];
        return "http://example.com/product/image" + generateRandomAlphanumericString(5, 20) + randomFormat;
    }

    public static String generateWeight() {
        double weight = Math.round(nextDouble() * 100.0 * 100) / 100.0;
        return String.format("%.2f", weight);
    }

    public static String generateDimensions() {
        return nextInt(5) + "x" + nextInt(5) + "x" + nextInt(5);
    }

    public static String generateManufacturer() {
        return generateRandomAlphanumericString(3, 100);
    }

    public static ProductRequest generate(String supplierId) {
        return generate(supplierId, generateImageUrl());
    }

    public static ProductRequest generate(String supplierId, String pathImg) {
        return ProductRequest.builder()
                .name(generateName())
                .description(generateDescription())
                .category(generateCategory())
                .price(generatePrice())
                .stockQuantity(generateStockQuantity())
                .supplierId(supplierId)
                .imageUrl(pathImg)
                .weight(generateWeight())
                .dimensions(generateDimensions())
                .manufacturer(generateManufacturer())
                .build();
    }

    public static ProductRequest generateOnlyMandatoryFields(String supplierId) {
        return ProductRequest.builder()
                .name(generateName())
                .price(generatePrice())
                .stockQuantity(generateStockQuantity())
                .supplierId(supplierId)
                .build();
    }
}
