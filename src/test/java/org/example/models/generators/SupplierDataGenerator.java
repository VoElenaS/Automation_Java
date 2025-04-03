package org.example.models.generators;

import static org.example.models.generators.BaseGenerator.*;

public class SupplierDataGenerator {

    public static String generateName() {
        return generateRandomString(10) + " Ltd";
    }

    public static String generateContactName() {
        return generateRandomString(10);
    }

    public static String generateContactEmail() {
        return generateRandomString(8) + "@test.com";
    }

    public static String generatePhoneNumber() {
        return "+" + generateRandomNumeric(nextInt(10) + 5);
    }

    public static String generateAddress() {
        return generateRandomStringWithNumbers(20);
    }

    public static String generateCountry() {
        return generateRandomString(7);
    }

    public static String generateCity() {
        return generateRandomString(7);
    }

    public static String generateWebsite() {
        return "https://www." + generateRandomLowerCaseString(5) + ".com";
    }
}
