package org.example.backend.requests;

import groovyjarjarantlr4.runtime.Token;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.example.backend.models.*;

public class ProductsServiceRequests {

    public static final String Product_service_Base_URL = "http://localhost:8002/";
    public static final String SUPPLIER_ENDPOIN = "suppliers/";

    public static SupplierCreateModel executePostCreateSupllier(SupplierCreateModel request, String accessToken) {
        return RestAssured.given()
                .baseUri(Product_service_Base_URL)
                .basePath(SUPPLIER_ENDPOIN)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(request)
                .when().post()
                .then().log().all()
                .extract().response().as(SupplierCreateModel.class);
    }




}
