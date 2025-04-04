package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.WarehouseRequest;
import org.example.models.response.WarehouseResponse;

public class WarehousesServicesAPI extends BaseAPI {
    public static final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8002/";
    public static final String WAREHOUSE_ENDPOINT = "warehouses/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public WarehouseResponse createWarehouse(WarehouseRequest request, String accessToken) {
        return createWarehouseWithResponse(request, accessToken).as(WarehouseResponse.class);
    }


    public Response createWarehouseWithResponse(WarehouseRequest request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response retrievingWarehouses(String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken));
    }

}
