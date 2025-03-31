package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.request.ChatRequest;
import org.example.backend.models.response.ChatResponse;

public class ChatsServicesAPI extends BaseAPI {
    public static final String CHAT_BASE_URL = "http://localhost:8004/";
    public static final String CHATS_ENDPOINT = "chats/";

    RequestSpecification chatsServiceRequest = new RequestSpecBuilder()
            .setBaseUri(CHAT_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public ChatResponse createChat(ChatRequest chatRequest, String accessToken) {
        RequestSpecification request = RestAssured.given(chatsServiceRequest).basePath(CHATS_ENDPOINT).header("Authorization", "Bearer " + accessToken).body(chatRequest);
        return sendRequestPost(request).as(ChatResponse.class);
    }

}
