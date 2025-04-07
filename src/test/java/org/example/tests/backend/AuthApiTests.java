package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.db.UsersQueries;
import org.example.db.models.UserDB;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.response.LoginResponse;
import org.example.models.response.RegisterResponse;
import org.example.models.response.UserInfo;
import org.example.models.response.UserTokenResponse;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthApiTests extends BaseTest {

    private static String registeredEmail;
    private static String registeredPassword;
    private static boolean isUserRegistered = false;

    @Test
    void registerUser() {
        if (!isUserRegistered) {
            RegisterRequest registerRequest = UserDataGenerator.generate();
            RegisterResponse registerResponse = authServiceAPI.registerUser(registerRequest);

            assertEquals("User successfully created", registerResponse.getMessage());
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
        Response pendingProducts = authServiceAPI.getPendingProducts(accessTokenSuperAdmin);

        assertEquals(200, pendingProducts.statusCode());
    }


    @Test
    void getPendingProductsRegularUser() {
        Response pendingProducts = authServiceAPI.getPendingProducts(accessToken);

        assertEquals(403, pendingProducts.statusCode());
    }

    @Test
    void getAccessToken() {
        if (!isUserRegistered) {
            registerUser();
        }

        LoginRequest loginRequest = LoginRequest.builder()
                .email(registeredEmail)
                .password(registeredPassword)
                .build();
        LoginResponse loginResponse = authServiceAPI.loginUser(loginRequest);
        System.out.println("access token " + (loginResponse.getAccessToken() != null ? loginResponse.getAccessToken() : "Token is null"));
        UserTokenResponse userToken = authServiceAPI.getUserToken(loginResponse.getUserId());
        assertNotNull(userToken, "The token wasn't created");
    }

    @Test
    void getAllUsers() {
        List<UserInfo> userInfos = authServiceAPI.retrieveUsers(accessTokenSuperAdmin);
        assertTrue(userInfos.size() > 1);
    }
}
