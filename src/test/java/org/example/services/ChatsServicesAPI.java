package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.AddMessageOnChatRequest;
import org.example.models.request.ChatRequest;
import org.example.models.response.AddMessageOnChatResponse;
import org.example.models.response.ChatAddUserResponse;
import org.example.models.response.ChatResponse;
import org.example.utils.TestProperties;

public class ChatsServicesAPI extends BaseAPI {
    public static final String CHAT_BASE_URL = TestProperties.properties.getProperty("chat_base_url");
    public static final String CHATS_ENDPOINT = TestProperties.properties.getProperty("chats_endpoint");
    public static final String MESSAGES_ENDPOINT = TestProperties.properties.getProperty("messages_endpoint");
    public static final String CHATS_ADD_USERS_ENDPOINT = TestProperties.properties.getProperty("chats_add_users_endpoint");

    RequestSpecification chatsServiceRequest = new RequestSpecBuilder()
            .setBaseUri(CHAT_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public ChatResponse createChat(ChatRequest chatRequest, String accessToken) {
        RequestSpecification request = RestAssured
                .given(chatsServiceRequest)
                .basePath(CHATS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(chatRequest);
        return sendRequestPost(request).as(ChatResponse.class);
    }

    public AddMessageOnChatResponse addMessageOnChat(String chatId, AddMessageOnChatRequest messageOnChatRequest, String accessToken) {
        RequestSpecification request = RestAssured
                .given(chatsServiceRequest)
                .basePath(MESSAGES_ENDPOINT)  // Use the new messages endpoint
                .pathParam("chatId", chatId)  // Set the chatId as a path parameter
                .header("Authorization", "Bearer " + accessToken)
                .body(messageOnChatRequest);
        return sendRequestPost(request).as(AddMessageOnChatResponse.class);  // Ensure correct response class
    }

    public ChatAddUserResponse addUserToChat(String userId, String chatId, String accessToken) {
        RequestSpecification request = RestAssured
                .given(chatsServiceRequest)
                .basePath(CHATS_ADD_USERS_ENDPOINT)  // Use the new messages endpoint
                .queryParam("user_id", userId)
                .queryParam("chat_id", chatId)// Set the chatId as a path parameter
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestPatch(request).as(ChatAddUserResponse.class);  // Ensure correct response class
    }


}
