package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.request.LoginRequest;
import org.example.backend.models.request.RegisterRequest;
import org.example.backend.models.response.LoginResponse;
import org.example.backend.models.response.RegisterResponse;
import org.example.backend.models.response.UserTokenResponse;

public class AuthServiceAPI extends BaseAPI {

    public static final String AUTH_SERVICE_BASE_URL = "http://localhost:8001/";
    public static final String REGISTER_ENDPOINT = "register/";
    public static final String GET_USER_TOKEN_ENDPOINT = "get-user-token/{user_id}/";
    public static final String LOGIN_ENDPOIN = "login/";
    public static final String GET_PENDING_PRODUCT_ENDPOINT = "get-pending-products/";

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
                .basePath(LOGIN_ENDPOIN))
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
