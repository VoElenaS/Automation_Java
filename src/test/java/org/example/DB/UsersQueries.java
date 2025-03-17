package org.example.DB;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.example.DB.models.UserDB;

import java.util.List;

import static org.example.DB.DBUtils.getConnection;

public class UsersQueries {
    @SneakyThrows
    public static UserDB getUserByName(String name) {
        String query = "SELECT * FROM users ";
        List<UserDB> users = new QueryRunner().query(getConnection(), query, new BeanListHandler<>(UserDB.class));
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElseThrow();
    }

    @SneakyThrows
    public static void setUserSuperAdminName(String id) {
        String query = "UPDATE users SET is_superadmin = TRUE WHERE id = ?::UUID";
        new QueryRunner().execute(getConnection(), query, id);

    }
}

