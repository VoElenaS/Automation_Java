package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ChatResponse {
    String name;
    @JsonProperty("is_group")
    boolean isGroup;
    String id;
    @JsonProperty("created_at")
    String createdAt;
    List<String> participants;
}
