package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.response.LoginResponse;
import org.example.models.response.RegisterResponse;
import org.example.models.response.UserTokenResponse;
import org.example.utils.TestProperties;

public class AuthServiceAPI extends BaseAPI {

    public static final String AUTH_SERVICE_BASE_URL = TestProperties.properties.getProperty("auth_service_base_url");
    public static final String LOGIN_ENDPOINT = TestProperties.properties.getProperty("login_endpoint");
    public static final String REGISTER_ENDPOINT = TestProperties.properties.getProperty("register_endpoint");
    public static final String GET_USER_TOKEN_ENDPOINT = TestProperties.properties.getProperty("get_user_token_endpoint");
    public static final String GET_PENDING_PRODUCT_ENDPOINT = TestProperties.properties.getProperty("get_pending_product_endpoint");

    private static final RequestSpecification authRequest = new RequestSpecBuilder()
            .setBaseUri(AUTH_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public RegisterResponse registerUser(RegisterRequest request) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .body(request)
                .basePath(REGISTER_ENDPOINT);
        return sendRequestPost(specification)
                .as(RegisterResponse.class);
    }

    public LoginResponse loginUser(LoginRequest request) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .body(request)
                .basePath(LOGIN_ENDPOINT);
        return sendRequestPost(specification)
                .as(LoginResponse.class);
    }

    public Response getPendingProducts(String accessToken) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_PENDING_PRODUCT_ENDPOINT)
                .header("Authorization", "bearer " + accessToken);
        return sendRequestGet(specification);
    }

    public UserTokenResponse getUserToken(String userId) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_USER_TOKEN_ENDPOINT)
                .pathParams("user_id", userId);
        return sendRequestGet(specification).as(UserTokenResponse.class);
    }
}
