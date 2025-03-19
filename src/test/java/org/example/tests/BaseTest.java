package org.example.tests;

import org.example.DB.DBUtils;
import org.example.backend.models.LoginRequest;
import org.example.backend.models.LoginResponse;
import org.example.backend.models.RegisterRequest;
import org.example.backend.requests.AuthServiceAPI;
import org.example.backend.requests.ProductsServiceAPI;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    public AuthServiceAPI authServiceAPI = new AuthServiceAPI();
    public ProductsServiceAPI productsServiceAPI = new ProductsServiceAPI();
    public static String accessToken;

    public static final String API_UI_URL = "http://localhost:8001/login";

    @AfterAll

    static void tearDown() {
        DBUtils.closeConnection();
    }

    @BeforeAll

    public static void setupUser() {
        AuthServiceAPI authServiceAPI = new AuthServiceAPI();
        RegisterRequest generateDataUserRequest = RegisterRequest.generate();
        authServiceAPI.registerUser(generateDataUserRequest);
        LoginResponse loginResponse = authServiceAPI.loginUser(LoginRequest.builder().email(generateDataUserRequest.getEmail()).password(generateDataUserRequest.getPassword()).build());

        accessToken = loginResponse.getAccessToken();

    }

}
