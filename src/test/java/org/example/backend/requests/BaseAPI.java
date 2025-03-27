package org.example.backend.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseAPI {

    public static Response sendRequestPost(RequestSpecification request) {
        return request.log().all()
                .when().post()
                .then().log().all()
                .extract().response();
    }

    public Response sendRequestGet(RequestSpecification request) {
        return request.log().all()
                .when().get()
                .then().log().all()
                .extract().response();
    }

    public Response sendRequestPatch(RequestSpecification request) {
        return request.log().all()
                .when().patch()
                .then().log().all()
                .extract().response();
    }

    public Response sendRequestPut(RequestSpecification request) {
        return request.log().all()
                .when().patch()
                .then().log().all()
                .extract().response();
    }

    public <T> T validaResponse(Response response, Class<T> clazz) {
        assertTrue(response.statusCode() < 400, "Error status code " + response.statusCode());
        return response.as(clazz);
    }
}
