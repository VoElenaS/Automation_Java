package org.example.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateUserDataResponse {
    String detail;
    UserResponse user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class UserResponse {
        String id;
        String name;
        String email;
    }
}
