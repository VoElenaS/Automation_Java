package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.ProductCreateModel;

import java.util.Map;

public class ProductsServicesAPI extends BaseAPI {

    public static final String Product_service_Base_URL = "http://localhost:8002/";
    public static final String PRODUCTS_ENDPOINT = "products/";
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_ID = "products/{productId}";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(Product_service_Base_URL)
            .setContentType(ContentType.JSON).build();


    public ProductCreateModel createProduct(ProductCreateModel request, String accessToken) {
        Response response = createProductWithResponse(request, accessToken);
        return validaResponse(response, ProductCreateModel.class);
    }

    public Response createProductWithResponse(ProductCreateModel request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response updateProductWithResponse(Map<String, Object> request, String productId, String accessToken) {
        return sendRequestPatch(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public ProductCreateModel updateProduct(Map<String, Object> request, String productId, String accessToken) {
        Response response = updateProductWithResponse(request, productId, accessToken);
        return validaResponse(response, ProductCreateModel.class);
    }

}
