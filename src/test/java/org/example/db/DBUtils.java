package org.example.db;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection connection;

    private static Connection createConnection() throws SQLException, ClassNotFoundException {

        String HOST = "localhost";
        int PORT = 5435;
        String DB_NAME = "strg_users_db";
        String USER = "storage_admin";
        String PASSWORD = "THw7l0bxvPPkWUhP";
        String TABLE = "users";

        String url = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;
        return DriverManager.getConnection(url, USER, PASSWORD);
    }

    @SneakyThrows
    public static Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    @SneakyThrows
    public static void closeConnection() {
        if (connection != null)
            connection.close();

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder

    public static class User {
        String id;
        String name;
        String email;
        String hashedPassword;
        boolean isSuperadmin;
    }
}



