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
        return sendRequestPost(RestAssured.given(authRequest)
                .body(request)
                .basePath(REGISTER_ENDPOINT))
                .as(RegisterResponse.class);
    }

    public LoginResponse loginUser(LoginRequest request) {
        return sendRequestPost(RestAssured.given(authRequest)
                .body(request)
                .basePath(LOGIN_ENDPOINT))
                .as(LoginResponse.class);
    }

    public Response getPendingProducts(String accessToken) {
        return sendRequestGet(RestAssured.given(authRequest)
                .basePath(GET_PENDING_PRODUCT_ENDPOINT)
                .header("Authorization", "bearer " + accessToken));
    }

    public UserTokenResponse getUserToken(String userId) {
        return sendRequestGet(RestAssured.given(authRequest)
                .basePath(GET_USER_TOKEN_ENDPOINT)
                .pathParams("user_id", userId)).as(UserTokenResponse.class);
    }
}
