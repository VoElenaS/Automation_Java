package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.SupplierCreateModel;

public class ProductsServiceAPI extends BaseAPI {

    public static final String Product_service_Base_URL = "http://localhost:8002/";
    public static final String SUPPLIER_ENDPOINT = "suppliers/";
    public static final String RETRIEVING_SUPPLIERS_ENDPOINT = "suppliers/";

    public static RequestSpecification supplierRequest = new RequestSpecBuilder()
            .setBaseUri(Product_service_Base_URL)
            .setContentType(ContentType.JSON).build();

    public SupplierCreateModel createSupplier(SupplierCreateModel request, String accessToken) {
        return createSupplierWithResponse(request, accessToken).as(SupplierCreateModel.class);
    }

    public Response createSupplierWithResponse(SupplierCreateModel request, String accessToken) {
        return sendRequestPost(RestAssured.given(supplierRequest)
                .basePath(SUPPLIER_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response retrievingSuppliers(String accessToken) {
        return sendRequestGet(RestAssured.given(supplierRequest)
                .basePath(RETRIEVING_SUPPLIERS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken));
    }
}
