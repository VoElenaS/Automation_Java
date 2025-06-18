package org.example.tests.backend;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.example.db.UsersQueries;
import org.example.db.models.UserDB;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.request.UserUpdateModel;
import org.example.models.response.*;
import org.example.tests.BaseApiTest;
import org.example.utils.ValidationUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Feature("User management")
public class AuthApiTests extends BaseApiTest {

    @Test
    void registerUser() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        RegisterResponse registerResponse = authServiceAPI.registerUser(registerRequest);
        log.info("User was created: {}", registerRequest.getName());

        assertEquals("User successfully created", registerResponse.getMessage());
        assertEquals(registerRequest.getEmail(), registerResponse.getUser().getEmail());
        assertEquals(registerRequest.getName(), registerResponse.getUser().getName());

        UserDB userFromDB = UsersQueries.getUserByName(registerResponse.getUser().getName());

        assertEquals(registerRequest.getName(), userFromDB.getName());
        assertEquals(registerRequest.getEmail(), userFromDB.getEmail());
    }

    @Test
    void shouldReturnPendingProductsForSuperAdmin() {
        Response pendingProducts = authServiceAPI.getPendingProductsResponse(accessTokenSuperAdmin);

        assertEquals(200, pendingProducts.statusCode());
    }

    @Test
    void pendingProductsValidations() {
        List<ProductResponse> products = authServiceAPI.getPendingProducts(accessTokenSuperAdmin);

        Validator temp = ValidationUtils.getValidator();

        for (ProductResponse product : products) {
            Set<ConstraintViolation<ProductResponse>> violations = ValidationUtils.getValidator().validate(product);

            assertTrue(violations.isEmpty(), "Validation errors: " + violations);

            LocalDateTime createdAt = LocalDateTime.parse(product.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime updatedAt = LocalDateTime.parse(product.getUpdatedAt(), DateTimeFormatter.ISO_DATE_TIME);

            assertTrue(createdAt.isBefore(updatedAt) || createdAt.isEqual(updatedAt),
                    "CreatedAt should be equal or before to updatedAt");

        }

        Set<String> names = new HashSet<>();
        for (ProductResponse product : products) {
            assertTrue(names.add(product.getName().toLowerCase()),
                    "The duplicate for product name was found " + product.getName());
        }
    }

    @Test
    void shouldDenyPendingProductsForRegularUser() {
        Response pendingProducts = authServiceAPI.getPendingProductsResponse(accessToken);

        assertEquals(403, pendingProducts.statusCode());
    }

    @Test
    void getAccessToken() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        RegisterResponse registerResponse = authServiceAPI.registerUser(registerRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
        LoginResponse loginResponse = authServiceAPI.loginUser(loginRequest);

        System.out.println("access token " + (loginResponse.getAccessToken() != null ? loginResponse.getAccessToken() : "Token is null"));
        UserTokenResponse userToken = authServiceAPI.getUserToken(loginResponse.getUserId());

        assertNotNull(userToken, "The token wasn't created");
    }

    @Test
    void getAllUsers() {
        List<UserInfo> users = authServiceAPI.retrieveUsers(accessTokenSuperAdmin);

        assertTrue(users.size() > 1);

        Set<String> names = new HashSet<>();
        for (UserInfo user : users) {
            assertTrue(names.add(user.getName().toLowerCase()), "The user name should be unique.");
        }

    }

    @Test
    void updateDataUser() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        RegisterResponse registerResponse = authServiceAPI.registerUser(registerRequest);
        String userId = registerResponse.getUser().getId();

        UserUpdateModel userUpdatedData = UserDataGenerator.generateNewData();
        UpdateUserDataResponse updateUserDataResponse = authServiceAPI.updateUserData(userUpdatedData, userId, accessTokenSuperAdmin);

        assertNotEquals(registerResponse.getUser().getName(), updateUserDataResponse.getUser().getName(), "The user name wasn't updated");
        assertNotEquals(registerResponse.getUser().getEmail(), updateUserDataResponse.getUser().getEmail(), "The email wasn't updated");
        assertEquals(registerResponse.getUser().getId(), updateUserDataResponse.getUser().getId(), "The userId is incorrect");
        assertEquals("User successfully updated", updateUserDataResponse.getDetail(), "User data wasn't updated");
    }
}
