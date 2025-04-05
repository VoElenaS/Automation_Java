package org.example.tests.backend;

import org.example.db.UsersQueries;
import org.example.models.generators.ChatDataGenerator;
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
    private static LoginResponse userRespons, participanteResponse;

    @Test
    @Order(0)
    void createChatTest() {

        RegisterRequest user = RegisterRequest.generate();
        RegisterResponse response = authServiceAPI.registerUser(user);
        RegisterRequest participante = RegisterRequest.generate();
        RegisterResponse responseParticipante = authServiceAPI.registerUser(participante);
        UsersQueries.setUserSuperAdminName(response.getUser().getId());

        userRespons = authServiceAPI.loginUser(LoginRequest.builder().email(user.getEmail()).password(user.getPassword()).build());
        participanteResponse = authServiceAPI.loginUser(LoginRequest.builder().email(participante.getEmail()).password(participante.getPassword()).build());
        ChatRequest chatRequest = ChatDataGenerator.generate(userRespons.getUserId(), participanteResponse.getUserId());
        chatResponse = chatServiceAPI.createChat(chatRequest, userRespons.getAccessToken());
    }

    @Test
    @Order(1)
        //@RepeatedTest(51)
    void addMessageToChat() {
        IntStream.range(0, 50).forEach(i ->
                chatServiceAPI.addMessageOnChat(chatResponse.getId(), ChatDataGenerator.ContentGeneration(chatResponse.getId()), userRespons.getAccessToken()));
    }
}
