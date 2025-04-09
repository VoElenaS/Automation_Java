package org.example.services;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseAPI {

    protected Response sendRequestPost(RequestSpecification request) {
        return sendRequest(request, Method.POST);
    }

    protected Response sendRequestGet(RequestSpecification request) {
        return sendRequest(request, Method.GET);
    }

    protected Response sendRequestPatch(RequestSpecification request) {
        return sendRequest(request, Method.PATCH);
    }

    protected Response sendRequestPut(RequestSpecification request) {
        return sendRequest(request, Method.PUT);
    }

    protected Response sendRequestDelete(RequestSpecification request) {
        return sendRequest(request, Method.DELETE);
    }

    protected <T> T validaResponse(Response response, Class<T> clazz) {
        assertTrue(response.statusCode() < 300, "Error status code " + response.statusCode());
        return response.as(clazz);
    }

    protected <T> T validaResponseCreated(Response response, Class<T> clazz) {
        assertEquals(201, response.statusCode(), "Error status code " + response.statusCode());
        return response.as(clazz);
    }

    protected <T> T validaResponseSuccessful(Response response, Class<T> clazz) {
        assertEquals(200, response.statusCode(), "Error status code " + response.statusCode());
        return response.as(clazz);
    }

    protected <T> T validaResponseBadRequest(Response response, Class<T> clazz) {
        assertEquals(400, response.statusCode(), "Error status code " + response.statusCode());
        return response.as(clazz);
    }

    protected <T> T validaResponseUnprocessableEntity(Response response, Class<T> clazz) {
        assertEquals(422, response.statusCode(), "Error status code " + response.statusCode());
        return response.as(clazz);
    }

    private Response sendRequest(RequestSpecification request, Method method) {
        return request.log().all()
                .when().request(method)
                .then().log().all()
                .extract().response();
    }
}
