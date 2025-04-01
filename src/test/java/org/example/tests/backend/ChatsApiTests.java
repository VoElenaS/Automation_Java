package org.example.tests.backend;

import org.example.DB.UsersQueries;
import org.example.backend.models.request.ChatRequest;
import org.example.backend.models.request.LoginRequest;
import org.example.backend.models.request.RegisterRequest;
import org.example.backend.models.response.ChatResponse;
import org.example.backend.models.response.LoginResponse;
import org.example.backend.models.response.RegisterResponse;
import org.example.tests.BaseTest;
import org.example.tests.frontend.models.ChatDataGenerator;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class ChatsApiTests extends BaseTest {
    public static ChatResponse chatResponse;
    private static LoginResponse userRespons;

    @Test
    @Order(0)
    void createChatTest() {

        RegisterRequest user = RegisterRequest.generate();
        RegisterResponse response = authServiceAPI.registerUser(user);
        UsersQueries.setUserSuperAdminName(response.getUser().getId());

        userRespons = authServiceAPI.loginUser(LoginRequest.builder().email(user.getEmail()).password(user.getPassword()).build());
        ChatRequest chatRequest = ChatDataGenerator.generate(userRespons.getUserId());
        chatResponse = chatServiceAPI.createChat(chatRequest, userRespons.getAccessToken());
    }

    @Test
    @Order(1)
    void addMessageToChat() {
        IntStream.range(0, 51).forEach(i ->
                chatServiceAPI.addMessageOnChat(chatResponse.getId(), ChatDataGenerator.ContentGeneration(chatResponse.getId()), userRespons.getAccessToken()));
    }
}
