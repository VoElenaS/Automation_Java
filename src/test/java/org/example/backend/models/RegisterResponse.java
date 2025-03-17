package org.example.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok annotation that automatically generates getters, setters, toString(), equals(), and hashCode() methods.
@AllArgsConstructor //Lombok annotation that generates a constructor with all fields.
@NoArgsConstructor //Lombok annotation that generates a default no-args constructor.
@Builder //Lombok annotation that enables the builder pattern for creating instances of this class.

public class RegisterResponse {
    String message;
    UserResp user;
}
