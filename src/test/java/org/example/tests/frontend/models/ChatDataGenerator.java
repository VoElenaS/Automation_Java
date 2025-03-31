package org.example.tests.frontend.models;

import org.example.backend.models.request.ChatRequest;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ChatDataGenerator {
    private static final Random random = new Random();

    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
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

    public static ChatRequest generate() {

        String name = generateName();
        boolean isGroup = true;
        List<String> participants = List.of("384edaba-11e4-4330-bd7e-d0ad77672a20", "384edaba-11e4-4330-bd7e-d0ad77672a20");

        return ChatRequest.builder().name(name).isGroup(isGroup).participants(participants).build();
    }
}
