package org.example.tests;

import org.example.DB.DBUtils;
import org.example.backend.requests.AuthServiceRequests;
import org.junit.jupiter.api.AfterAll;

public abstract class BaseTest {

    public AuthServiceRequests authServiceRequests = new AuthServiceRequests();

    public static final String API_UI_URL = "http://localhost:8001/login";

    @AfterAll

    static void tearDown() {
        DBUtils.closeConnection();
    }
}
