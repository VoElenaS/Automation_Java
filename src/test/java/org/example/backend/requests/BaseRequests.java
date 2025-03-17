package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.RegisterResponse;

public class BaseRequests {
    public void sendRequest(RequestSpecification request, Object body){

//        RestAssured.given(authRequest)
//                .basePath(register_ENDPOIN)
//                .body(request)
//                .when().post()
//                .then().log().all()
//                .extract().response().as(RegisterResponse.class);
//
//
//        RestAssured.given(authRequest)
//                .basePath(register_ENDPOIN)
//                .body(request)
//                .when().post()
//                .then().log().all()
//                .extract().response().as(RegisterResponse.class);
    }
}
