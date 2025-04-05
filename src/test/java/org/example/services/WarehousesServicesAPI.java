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
    public static final String WAREHOUSE_PRODUCT_ENDPOIN = "productinwarehouses";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public WarehouseResponse createWarehouse(WarehouseRequest request, String accessToken) {
        return createWarehouseWithResponse(request, accessToken).as(WarehouseResponse.class);
    }


    public Response createWarehouseWithResponse(WarehouseRequest request, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request);

        return sendRequestPost(specification);
    }

    public Response retrievingWarehouses(String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken);

        return sendRequestGet(specification);
    }

    public Response addProductInWarehouse(String accessToken, String warehouseId, String productId, int quantity) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_PRODUCT_ENDPOIN)
                .queryParam("warehouse_id", warehouseId)
                .queryParam("product_id", productId)
                .queryParam("quantity", quantity)
                .header("Authorization", "Bearer " + accessToken);

        return sendRequestPost(specification);
    }

}
