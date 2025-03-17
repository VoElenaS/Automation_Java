package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    @JsonProperty ("user_id")
    String userId;
    String message;
    @JsonProperty ("access_token")
    String accessToken;
    @JsonProperty ("refresh_token")
    String refreshToken;
    @JsonProperty ("token_type")
    String tokenType;
}