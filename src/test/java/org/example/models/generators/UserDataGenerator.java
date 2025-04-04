package org.example.models.generators;

import org.apache.commons.lang3.RandomStringUtils;

public class UserDataGenerator {


    public static String generateName() {
        return RandomStringUtils.randomAlphabetic(3, 50);
    }

    public static String generateEmail() {
        return "user_" + generateName().toLowerCase() + "@mail.ru";
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8, 16);
    }
}
