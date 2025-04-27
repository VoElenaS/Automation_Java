package org.example.tests;

import org.example.db.DBUtils;
import org.example.db.UsersQueries;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.response.LoginResponse;
import org.example.services.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    public static final String API_UI_URL = "http://localhost:8001/login";
    public static String accessToken;
    public static String userId;
    public static String accessTokenSuperAdmin;
    public static String superAdminId;
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

    public static void setupRegularUser() {
        AuthServiceAPI authServiceAPI = new AuthServiceAPI();
        RegisterRequest generateDataUserRequest = UserDataGenerator.generate();
        authServiceAPI.registerUser(generateDataUserRequest);
        LoginResponse loginResponse = authServiceAPI
                .loginUser(LoginRequest.builder().email(generateDataUserRequest.getEmail()).password(generateDataUserRequest.getPassword()).build());
        accessToken = loginResponse.getAccessToken();
        userId = loginResponse.getUserId();
    }

    @BeforeAll
    public static void setupSuperAdmin() {
        AuthServiceAPI authServiceAPI = new AuthServiceAPI();
        RegisterRequest request = UserDataGenerator.generate();
        authServiceAPI.registerUser(request);
        LoginResponse loginResponse = authServiceAPI.loginUser(new LoginRequest(request.getEmail(), request.getPassword()));
        UsersQueries.setUserSuperAdminName(loginResponse.getUserId());
        accessTokenSuperAdmin = loginResponse.getAccessToken();
        superAdminId = loginResponse.getUserId();
    }
}

