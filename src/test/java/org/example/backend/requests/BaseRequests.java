package org.example.backend.requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseRequests {
    public Response sendRequestPost(RequestSpecification request) {

        return request
                .when().post()
                .then().log().all()
                .extract().response();

    }

    public Response sendRequestGet(RequestSpecification request) {

        return request
                .when().get()
                .then().log().all()
                .extract().response();

    }
}
