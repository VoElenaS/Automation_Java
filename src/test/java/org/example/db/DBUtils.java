package org.example.db;

import lombok.*;
import org.example.utils.TestProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    public static Connection connection;

    private static Connection createConnection() throws SQLException, ClassNotFoundException {

        String HOST = TestProperties.properties.getProperty("host");
        int PORT = Integer.parseInt(TestProperties.properties.getProperty("port"));
        String DB_NAME = TestProperties.properties.getProperty("db_name");
        String USER = TestProperties.properties.getProperty("user");
        String PASSWORD = TestProperties.properties.getProperty("password");
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



