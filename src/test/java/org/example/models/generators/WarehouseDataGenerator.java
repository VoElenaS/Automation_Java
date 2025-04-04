package org.example.models.generators;

import org.example.models.WarehouseModel;

import static org.apache.commons.lang3.RandomUtils.nextBoolean;
import static org.example.models.generators.BaseGenerator.*;

public class WarehouseDataGenerator {

    public static String generateLocation() {
        return generateRandomAlphanumericString(1, 255);
    }

    public static String generateManagerName() {
        return generateRandomAlphaString(1, 100);
    }

    public static Integer generateCapacity() {
        return nextInt(100);
    }

    public static Integer generateCurrentStock() {
        return nextInt(100);
    }

    public static String generateContactNumber() {
        return "+" + generateRandomNumericString(3, 14);
    }

    public static String generateEmail() {
        return generateRandomAlphaString(17, 20) + "@" + generateRandomAlphaString(4, 6) + "." + generateRandomAlphaString(2, 3);
    }

    public static Boolean generateIsActive() {
        return nextBoolean();
    }

    public static String generateAreaSize() {
        double area = Math.round((nextDouble() * 9999.99) * 100) / 100.0;
        return String.format("%.2f", area);
    }

    public static WarehouseModel generate() {
        return WarehouseModel.builder()
                .location(generateLocation())
                .managerName(generateManagerName())
                .capacity(generateCapacity())
                .currentStock(generateCurrentStock())
                .contactNumber(generateContactNumber())
                .email(generateEmail())
                .isActive(generateIsActive())
                .areaSize(generateAreaSize())
                .build();
    }
}

