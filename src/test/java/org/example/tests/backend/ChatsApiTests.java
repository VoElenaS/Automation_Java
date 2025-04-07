package org.example.tests.backend;

import org.example.models.generators.ChatDataGenerator;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.ChatRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.response.ChatResponse;
import org.example.models.response.LoginResponse;
import org.example.models.response.RegisterResponse;
import org.example.models.response.UserInfo;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.IntStream;

public class ChatsApiTests extends BaseTest {
    public static ChatResponse chatResponse;
    private static LoginResponse userResponse;

    @Test
    @Order(0)
    void createChatTest() {
        RegisterRequest participant = UserDataGenerator.generate();
        RegisterResponse participantResponse = authServiceAPI.registerUser(participant);

        ChatRequest chatRequest = ChatDataGenerator.generate(superAdminId, participantResponse.getUser().getId());
        chatResponse = chatServiceAPI.createChat(chatRequest, accessTokenSuperAdmin);
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 50, 100})
    @Order(1)
        //@RepeatedTest(51)
    void addMessageToChat(int messages) {
        IntStream.range(0, messages).forEach(i ->
                chatServiceAPI.addMessageOnChat(chatResponse.getId(), ChatDataGenerator.contentGeneration(chatResponse.getId()), accessTokenSuperAdmin));
    }

    @Test
    void addUserToChat() {
        List<UserInfo> userInfos = authServiceAPI.retrieveUsers(accessTokenSuperAdmin);

        userInfos.stream()
                .map(u -> u.getUserId())
                .filter(id -> !id.equals(superAdminId))
                .forEach(id -> chatServiceAPI.addUserToChat(id, chatResponse.getId(), accessTokenSuperAdmin));
    }
}
