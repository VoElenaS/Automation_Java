package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.LoginRequest;
import org.example.models.request.RegisterRequest;
import org.example.models.request.UserUpdateModel;
import org.example.models.response.*;
import org.example.utils.TestProperties;

import java.io.IOException;
import java.util.List;

public class AuthServiceAPI extends BaseAPI {

    public static final String AUTH_SERVICE_BASE_URL = TestProperties.properties.getProperty("auth_service_base_url");
    public static final String LOGIN_ENDPOINT = TestProperties.properties.getProperty("login_endpoint");
    public static final String REGISTER_ENDPOINT = TestProperties.properties.getProperty("register_endpoint");
    public static final String GET_USER_TOKEN_ENDPOINT = TestProperties.properties.getProperty("get_user_token_endpoint");
    public static final String GET_PENDING_PRODUCT_ENDPOINT = TestProperties.properties.getProperty("get_pending_product_endpoint");
    public static final String GET_USERS_ENDPOINT = TestProperties.properties.getProperty("get_users_endpoint");
    public static final String UPDATE_USER_ENDPOINT = TestProperties.properties.getProperty("update_user_endpoint");

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

    public UpdateUserDataResponse updateUserData(UserUpdateModel request, String userId, String accessToken) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(UPDATE_USER_ENDPOINT)
                .pathParams("user_id", userId)
                .body(request)
                .header("Authorization", "bearer " + accessToken);
        return sendRequestPut(specification).as(UpdateUserDataResponse.class);
    }

    public Response getPendingProductsResponse(String accessToken) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_PENDING_PRODUCT_ENDPOINT)
                .header("Authorization", "bearer " + accessToken);
        return sendRequestGet(specification);
    }

    public List<ProductResponse> getPendingProducts(String accessToken) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_PENDING_PRODUCT_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken);

        Response response = sendRequestGet(specification);
        if (response.statusCode() == 200) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.getBody().asString(), new TypeReference<List<ProductResponse>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse response to list", e);
            }
        } else {
            throw new RuntimeException("Failed to get pending products. Status: " + response.statusCode());
        }
    }

    public UserTokenResponse getUserToken(String userId) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_USER_TOKEN_ENDPOINT)
                .pathParams("user_id", userId);
        return validaResponse(sendRequestGet(specification), UserTokenResponse.class);
    }

    public List<UserInfo> retrieveUsers(String accessToken) {
        RequestSpecification specification = RestAssured.given(authRequest)
                .basePath(GET_USERS_ENDPOINT)
                .header("Authorization", "bearer " + accessToken);
        return sendRequestGet(specification).jsonPath().getList("$", UserInfo.class);
    }
}
