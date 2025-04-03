package org.example.services;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseAPI {

    protected Response sendRequestPost(RequestSpecification request) {
        return request.log().all()
                .when().post()
                .then().log().all()
                .extract().response();
    }

    protected Response sendRequestGet(RequestSpecification request) {
        return request.log().all()
                .when().get()
                .then().log().all()
                .extract().response();
    }

    protected Response sendRequestPatch(RequestSpecification request) {
        return request.log().all()
                .when().patch()
                .then().log().all()
                .extract().response();
    }

    protected Response sendRequestPut(RequestSpecification request) {
        return request.log().all()
                .when().put()
                .then().log().all()
                .extract().response();
    }

    protected Response sendRequestDelete(RequestSpecification request) {
        return request.log().all()
                .when().delete()
                .then().log().all()
                .extract().response();
    }

    protected <T> T validaResponse(Response response, Class<T> clazz) {
        assertTrue(response.statusCode() < 400, "Error status code " + response.statusCode());
        return response.as(clazz);
    }
}
