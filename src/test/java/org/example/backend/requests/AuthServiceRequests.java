package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.*;

public class AuthServiceRequests {

    public static final String Auth_Service_Base_URL = "http://localhost:8001/";
    public static final String register_ENDPOIN = "register/";
    public static final String getUserToken_ENDPOIN = "get-user-token/{user_id}/";
    public static final String login_ENDPOIN = "login/";
    public static final String GET_Pending_Product_ENDPOIN = "get-pending-products/";

    private static RequestSpecification authRequest = new RequestSpecBuilder().setBaseUri(Auth_Service_Base_URL).setContentType(ContentType.JSON).build();

    public static RegisterResponse registerUser(RegisterRequest request) {

        return RestAssured.given(authRequest)
               .basePath(register_ENDPOIN)
               .body(request)
               .when().post()
               .then().log().all()
               .extract().response().as(RegisterResponse.class);
          }

    public static LoginResponse postLogin(LoginRequest request) {
        return RestAssured.given()
                .baseUri(Auth_Service_Base_URL)
                .basePath(login_ENDPOIN)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post()
                .then().log().all()
                .extract().response().as(LoginResponse.class);
    }

    public static Response getPendingProducts (String accessToken){
        return RestAssured.given()
                .baseUri(Auth_Service_Base_URL)
                .basePath(GET_Pending_Product_ENDPOIN)
                .header("Authorization", "bearer " + accessToken)
                .contentType(ContentType.JSON).log().all()
                .when().get()
                .then().log().all()
                .extract().response();
    }

    public static UserTokenResponse getUserToken (String userId){
        return RestAssured.given()
                .baseUri(Auth_Service_Base_URL)
                .basePath(getUserToken_ENDPOIN)
                .pathParams("user_id", userId)
                .contentType(ContentType.JSON).log().all()
                .when().get()
                .then().log().all()
                .extract().response().as(UserTokenResponse.class);
    }


}
