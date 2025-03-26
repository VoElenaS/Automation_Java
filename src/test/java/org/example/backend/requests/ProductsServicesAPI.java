package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.ProductCreateModel;

public class ProductsServicesAPI extends BaseAPI {

    public static final String Product_service_Base_URL = "http://localhost:8002/";
    public static final String PRODUCTS_ENDPOINT = "products/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(Product_service_Base_URL)
            .setContentType(ContentType.JSON).build();


    public ProductCreateModel createProduct(ProductCreateModel request, String accessToken) {
        return createProductWithResponse(request, accessToken).as(ProductCreateModel.class);
    }

    public Response createProductWithResponse(ProductCreateModel request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response updateProductWithResponse(ProductCreateModel request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }


}
