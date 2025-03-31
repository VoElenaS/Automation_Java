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
import org.junit.jupiter.api.Test;

public class ChatsApiTests extends BaseTest {

    @Test
    void createChatTest() {

        RegisterRequest user = RegisterRequest.generate();
        RegisterResponse response = authServiceAPI.registerUser(user);
        UsersQueries.setUserSuperAdminName(response.getUser().getId());

        LoginResponse userRespons = authServiceAPI.loginUser(LoginRequest.builder().email(user.getEmail()).password(user.getPassword()).build());
        ChatRequest chatRequest = ChatDataGenerator.generate();
        ChatResponse chatResponse = chatServiceAPI.createChat(chatRequest, userRespons.getAccessToken());
    }


}
