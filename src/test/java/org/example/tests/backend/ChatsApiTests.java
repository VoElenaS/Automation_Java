package org.example.tests.backend;

import org.example.db.UsersQueries;
import org.example.models.generators.ChatDataGenerator;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.ChatRequest;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.response.ChatResponse;
import org.example.models.response.LoginResponse;
import org.example.models.response.RegisterResponse;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ChatsApiTests extends BaseTest {
    public static ChatResponse chatResponse;
    private static LoginResponse userResponse;

    @Test
    @Order(0)
    void createChatTest() {

        RegisterRequest user = UserDataGenerator.generate();
        RegisterResponse response = authServiceAPI.registerUser(user);
        RegisterRequest participant = UserDataGenerator.generate();
        RegisterResponse participantResponse = authServiceAPI.registerUser(participant);
        UsersQueries.setUserSuperAdminName(response.getUser().getId());

        userResponse = authServiceAPI.loginUser(LoginRequest.builder().email(user.getEmail()).password(user.getPassword()).build());
        ChatRequest chatRequest = ChatDataGenerator.generate(userResponse.getUserId(), participantResponse.getUser().getId());
        chatResponse = chatServiceAPI.createChat(chatRequest, userResponse.getAccessToken());
    }

    @Test
    @Order(1)
        //@RepeatedTest(51)
    void addMessageToChat() {
        IntStream.range(0, 50).forEach(i ->
                chatServiceAPI.addMessageOnChat(chatResponse.getId(), ChatDataGenerator.ContentGeneration(chatResponse.getId()), userResponse.getAccessToken()));
    }
}
