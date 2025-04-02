package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.AddMessageOnChatRequest;
import org.example.models.request.ChatRequest;
import org.example.models.response.AddMessageOnChatResponse;
import org.example.models.response.ChatResponse;

public class ChatsServicesAPI extends BaseAPI {
    public static final String CHAT_BASE_URL = "http://localhost:8004/";
    public static final String CHATS_ENDPOINT = "chats/";
    public static final String MESSAGES_ENDPOINT = "chats/{chatId}/messages";

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


}
