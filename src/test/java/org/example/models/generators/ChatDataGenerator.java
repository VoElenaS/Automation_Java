package org.example.models.generators;

import org.example.models.request.AddMessageOnChatRequest;
import org.example.models.request.ChatRequest;

import java.util.List;

import static org.example.models.generators.BaseGenerator.generateRandomAlphanumericString;

public class ChatDataGenerator {

    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
    }

    public static ChatRequest generate(String owner, String participant) {
        String name = generateName();
        boolean isGroup = true;
        List<String> participants = List.of(owner, participant);
        return ChatRequest.builder().name(name).isGroup(isGroup).participants(participants).build();
    }

    public static AddMessageOnChatRequest ContentGeneration(String chatId) {
        String content = generateName();
        return new AddMessageOnChatRequest(chatId, content);
    }

}
