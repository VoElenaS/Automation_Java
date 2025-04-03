package org.example.models.generators;

import org.example.models.request.AddMessageOnChatRequest;
import org.example.models.request.ChatRequest;

import java.util.List;

import static org.example.models.generators.BaseGenerator.generateRandomAlphanumericString;

public class ChatDataGenerator {

    public static String generateName() {
        return generateRandomAlphanumericString(3, 100);
    }

    public static ChatRequest generate(String owner) {
        String name = generateName();
        boolean isGroup = true;
        List<String> participants = List.of(owner, "384edaba-11e4-4330-bd7e-d0ad77672a20");
        return ChatRequest.builder().name(name).isGroup(isGroup).participants(participants).build();
    }

    public static AddMessageOnChatRequest ContentGeneration(String chatId) {
        String content = generateName();
        return new AddMessageOnChatRequest(chatId, content);
    }

}
