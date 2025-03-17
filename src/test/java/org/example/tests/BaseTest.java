package org.example.tests;

import org.example.DB.DBUtils;
import org.junit.jupiter.api.AfterAll;

public abstract class BaseTest {

    public static final String API_UI_URL = "http://localhost:8001/login";

    @AfterAll

    static void tearDown() {
        DBUtils.closeConnection();
    }
}
