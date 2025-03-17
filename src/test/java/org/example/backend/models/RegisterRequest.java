package org.example.backend.models;

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

        // Generate a random name (3-50 alphabetic characters, no spaces or symbols)
        String name = RandomStringUtils.randomAlphabetic(3, 50);

        // Generate a valid email using the name
        String email = "user_" + name.toLowerCase() + "@mail.ru";

        // Generate a strong password (8-16 alphanumeric characters)
        String password = RandomStringUtils.randomAlphanumeric(8, 16);

        return RegisterRequest.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }

};
