package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.SupplierModel;

public class SuppliersServicesAPI extends BaseAPI {

    public static final String Product_service_Base_URL = "http://localhost:8002/";
    public static final String SUPPLIER_ENDPOINT = "suppliers/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(Product_service_Base_URL)
            .setContentType(ContentType.JSON)
            .build();

    public SupplierModel createSupplier(SupplierModel request, String accessToken) {
        return createSupplierWithResponse(request, accessToken).as(SupplierModel.class);
    }

    public Response createSupplierWithResponse(SupplierModel request, String accessToken) {
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
