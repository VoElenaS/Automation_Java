package org.example.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class RegisterRequest {

    String name;
    String email;
    String password;

}
