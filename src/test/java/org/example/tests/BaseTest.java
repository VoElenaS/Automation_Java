package org.example.tests;

import org.example.DB.DBUtils;
import org.example.backend.models.request.LoginRequest;
import org.example.backend.models.request.RegisterRequest;
import org.example.backend.models.response.LoginResponse;
import org.example.backend.requests.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    public static final String API_UI_URL = "http://localhost:8001/login";
    public static String accessToken;
    public static String userId;
    public AuthServiceAPI authServiceAPI = new AuthServiceAPI();
    public SuppliersServicesAPI suppliersServicesAPI = new SuppliersServicesAPI();
    public ProductsServicesAPI productsServicesAPI = new ProductsServicesAPI();
    public WarehousesServicesAPI warehousesServicesAPI = new WarehousesServicesAPI();
    public ChatsServicesAPI chatServiceAPI = new ChatsServicesAPI();

    @AfterAll

    static void tearDown() {
        DBUtils.closeConnection();
    }

    @BeforeAll

    public static void setupUser() {
        AuthServiceAPI authServiceAPI = new AuthServiceAPI();
        RegisterRequest generateDataUserRequest = RegisterRequest.generate();
        authServiceAPI.registerUser(generateDataUserRequest);
        LoginResponse loginResponse = authServiceAPI
                .loginUser(LoginRequest.builder().email(generateDataUserRequest.getEmail()).password(generateDataUserRequest.getPassword()).build());
        accessToken = loginResponse.getAccessToken();
        userId = loginResponse.getUserId();
    }

}
