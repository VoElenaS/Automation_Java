package org.example.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@Builder

public class RegisterRequest {

    String name;
    String email;
    String password;

    public static RegisterRequest generate() {
        String name = RandomStringUtils.randomAlphabetic(3, 50);
        String email = "user_" + name.toLowerCase() + "@mail.ru";
        String password = RandomStringUtils.randomAlphanumeric(8, 16);
        return RegisterRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
