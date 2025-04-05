package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.WarehouseRequest;
import org.example.models.response.ProductsInWarehouseResponse;
import org.example.models.response.WarehouseResponse;

public class WarehousesServicesAPI extends BaseAPI {
    public static final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8002/";
    public static final String WAREHOUSE_ENDPOINT = "warehouses/";
    public static final String WAREHOUSE_PRODUCT_ENDPOIN = "productinwarehouses";
    public static final String WAREHOUSE_PRODUCT_WITH_ID_ENDPOIN = "warehouses/{warehouseId}";

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

    public WarehouseResponse retrievingWarehousesById(String warehouseId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_PRODUCT_WITH_ID_ENDPOIN)
                .pathParams("warehouseId", warehouseId)
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestGet(specification).as(WarehouseResponse.class);
    }


    public Response addProductInWarehouseWithRespons(String accessToken, String warehouseId, String productId, int quantity) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(WAREHOUSE_PRODUCT_ENDPOIN)
                .queryParam("warehouse_id", warehouseId)
                .queryParam("product_id", productId)
                .queryParam("quantity", quantity)
                .header("Authorization", "Bearer " + accessToken);

        return sendRequestPost(specification);
    }

    public ProductsInWarehouseResponse addProductInWarehouse(String accessToken, String warehouseId, String productId, int quantity) {
        return addProductInWarehouseWithRespons(accessToken, warehouseId, productId, quantity).as(ProductsInWarehouseResponse.class);
    }

}
