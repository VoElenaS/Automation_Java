package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.ProductPatchModel;
import org.example.models.request.ProductRequest;
import org.example.models.response.ProductResponse;

public class ProductsServicesAPI extends BaseAPI {

    public static final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8002/";
    public static final String PRODUCTS_ENDPOINT = "products/";
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_ID = "products/{productId}";
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_NAME = "search_products/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON).build();


    public ProductResponse createProduct(ProductRequest request, String accessToken) {
        Response response = createProductWithResponse(request, accessToken);
        return validaResponse(response, ProductResponse.class);
    }

    public Response createProductWithResponse(ProductRequest request, String accessToken) {
        return sendRequestPost(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public Response updateProductWithResponse(ProductPatchModel request, String productId, String accessToken) {
        return sendRequestPatch(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request));
    }

    public ProductResponse updateProductIsAvailable(ProductPatchModel request, String productId, String accessToken) {
        Response response = updateProductWithResponse(request, productId, accessToken);
        return validaResponse(response, ProductResponse.class);
    }

    public ProductResponse getProduct(String productId, String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
        )
                .as(ProductResponse.class);
    }

    public Response retrievingProducts(String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken));
    }

    public ProductResponse updateProductById(ProductRequest request, String productId, String accessToken) {
        return sendRequestPut(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)).as(ProductResponse.class);
    }

    public Response deleteProduct(String productId, String accessToken) {
        return sendRequestDelete(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken));
    }

    public Response retrievingProductByName(String name, String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_NAME)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("name", name));
    }

}
