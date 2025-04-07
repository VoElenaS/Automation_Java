package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class UserInfo {

    @JsonProperty("id")
    String userId;
    String name;
    String email;
    String role;
}
