package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.*;

public class AuthServiceAPI extends BaseAPI {

    public static final String Auth_Service_Base_URL = "http://localhost:8001/";
    public static final String register_ENDPOINT = "register/";
    public static final String getUserToken_ENDPOINT = "get-user-token/{user_id}/";
    public static final String login_ENDPOIN = "login/";
    public static final String GET_Pending_Product_ENDPOINT = "get-pending-products/";

    private static RequestSpecification authRequest = new RequestSpecBuilder()
            .setBaseUri(Auth_Service_Base_URL)
            .setContentType(ContentType.JSON)
            .build();

    public RegisterResponse registerUser(RegisterRequest request) {
        return sendRequestPost(RestAssured.given(authRequest)
                .body(request)
                .basePath(register_ENDPOINT))
                .as(RegisterResponse.class);
    }

    public LoginResponse loginUser(LoginRequest request) {
        return sendRequestPost(RestAssured.given(authRequest)
                .body(request)
                .basePath(login_ENDPOIN))
                .as(LoginResponse.class);
    }

    public Response getPendingProducts(String accessToken) {
        return sendRequestGet(RestAssured.given(authRequest)
                .basePath(GET_Pending_Product_ENDPOINT)
                .header("Authorization", "bearer " + accessToken));
    }

    public UserTokenResponse getUserToken(String userId) {
        return sendRequestGet(RestAssured.given(authRequest)
                .basePath(getUserToken_ENDPOINT)
                .pathParams("user_id", userId)).as(UserTokenResponse.class);
    }
}
