package org.example.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.models.generators.UserDataGenerator;

@Data
@AllArgsConstructor
@Builder

public class RegisterRequest {

    String name;
    String email;
    String password;

    public static RegisterRequest generate() {
        return RegisterRequest.builder()
                .email(UserDataGenerator.generateEmail())
                .name(UserDataGenerator.generateName())
                .password(UserDataGenerator.generatePassword())
                .build();
    }
}
