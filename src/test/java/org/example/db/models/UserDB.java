package org.example.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDB {
    String id;
    String name;
    String email;
    String hashedPassword;
    boolean isSuperadmin;
}
