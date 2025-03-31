package org.example.backend.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ChatRequest {
    String name;
    @JsonProperty("is_group")
    boolean isGroup;
    List<String> participants;
}
