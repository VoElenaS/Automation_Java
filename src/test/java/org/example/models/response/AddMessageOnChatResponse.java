package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AddMessageOnChatResponse {
    @JsonProperty("chat_id")
    String chatId;
    String content;
    String id;
    @JsonProperty("created_at")
    String createdAt;
}
