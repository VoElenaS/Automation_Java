package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.DB.UsersQueries;
import org.example.DB.models.UserDB;
import org.example.backend.models.*;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthApiTests extends BaseTest {


    private static String registeredEmail;
    private static String registeredPassword;
    private static boolean isUserRegistered = false;

    @Test
    void registerUser() {
        if (!isUserRegistered) { // Register only once
            RegisterRequest registerRequest = RegisterRequest.generate();
            RegisterResponse registerResponse = authServiceAPI.registerUser(registerRequest);

            assertEquals(registerResponse.getMessage(), "User successfully created");
            assertEquals(registerRequest.getEmail(), registerResponse.getUser().getEmail());
            assertEquals(registerRequest.getName(), registerResponse.getUser().getName());

            registeredEmail = registerRequest.getEmail();
            registeredPassword = registerRequest.getPassword();
            isUserRegistered = true;

            UserDB userFromDB = UsersQueries.getUserByName(registerResponse.getUser().getName());

            assertEquals(registerRequest.getName(), userFromDB.getName());
            assertEquals(registerRequest.getEmail(), userFromDB.getEmail());

        }
    }

    @Test
    void getPendingProductsSuperAdminTest() {
        if (!isUserRegistered) {
            registerUser(); // Ensure a user is registered before login
        }

        // Use the stored credentials for login
        LoginRequest loginRequest = LoginRequest.builder()
                .email(registeredEmail)
                .password(registeredPassword)
                .build();

        LoginResponse loginResponse = authServiceAPI.postLogin(loginRequest);

        UsersQueries.setUserSuperAdminName(loginResponse.getUserId());

        Response pendingProducts = authServiceAPI.getPendingProducts(loginResponse.getAccessToken());

        assertEquals(200, pendingProducts.statusCode());
    }

    @Test
    void getPendingProductsRegularUser() {
        if (!isUserRegistered) {
            registerUser(); // Ensure a user is registered before login
        }

        // Use the stored credentials for login
        LoginRequest loginRequest = LoginRequest.builder()
                .email(registeredEmail)
                .password(registeredPassword)
                .build();

        LoginResponse loginResponse = authServiceAPI.postLogin(loginRequest);

        Response pendingProducts = authServiceAPI.getPendingProducts(loginResponse.getAccessToken());

        assertEquals(200, pendingProducts.statusCode());
        //assertEquals("Insufficient rights to view pending products", pendingProducts.jsonPath().get("detail"));
    }

    @Test
    void getAccessToken() {

        if (!isUserRegistered) {
            registerUser(); // Ensure a user is registered before login
        }
        // Use the stored credentials for login
        LoginRequest loginRequest = LoginRequest.builder()
                .email(registeredEmail)
                .password(registeredPassword)
                .build();

        LoginResponse loginResponse = authServiceAPI.postLogin(loginRequest);

        System.out.println("access token "+ (loginResponse.getAccessToken() != null ? loginResponse.getAccessToken() : "Token is null"));

        UserTokenResponse userToken = authServiceAPI.getUserToken(loginResponse.getUserId());
    }


}
