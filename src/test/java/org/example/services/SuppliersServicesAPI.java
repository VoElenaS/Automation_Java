package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.SupplierRequest;
import org.example.models.response.SupplierResponse;
import org.example.utils.TestProperties;

public class SuppliersServicesAPI extends BaseAPI {

    public static final String PRODUCT_SERVICE_BASE_URL = TestProperties.properties.getProperty("product_service_base_url");
    public static final String SUPPLIER_ENDPOINT = TestProperties.properties.getProperty("supplier_endpoint");

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public SupplierResponse createSupplier(SupplierRequest request, String accessToken) {
        return createSupplierWithResponse(request, accessToken).as(SupplierResponse.class);
    }

    public Response createSupplierWithResponse(SupplierRequest request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(SUPPLIER_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response retrievingSuppliers(String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(SUPPLIER_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken));
    }
}
