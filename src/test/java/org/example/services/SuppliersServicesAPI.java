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
    public static final String SUPPLIER_ENDPOINT_WITH_SUPPLIER_ID = TestProperties.properties.getProperty("supplier_endpoint_with_supplier_id");

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public SupplierResponse createSupplier(SupplierRequest request, String accessToken) {
        return validaResponse(createSupplierWithResponse(request, accessToken), SupplierResponse.class);
    }

    public Response createSupplierWithResponse(SupplierRequest request, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(SUPPLIER_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request);
        return sendRequestPost(specification);
    }

    public Response retrievingSuppliers(String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(SUPPLIER_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestGet(specification);
    }

    public Response retrievingSuppliersById(String supplierId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(SUPPLIER_ENDPOINT_WITH_SUPPLIER_ID)
                .pathParams("supplierId", supplierId)
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestGet(specification);
    }
}
