package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.WarehouseModel;

public class WarehousesServicesAPI extends BaseAPI {
    public static final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8002/";
    public static final String WAREHOUSE_ENDPOINT = "warehouses/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public Response createWarehouse(WarehouseModel request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }
}
