package org.example.backend.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.backend.models.ProductModel;
import org.example.backend.models.ProductPatchModel;

public class ProductsServicesAPI extends BaseAPI {

    public static final String PRODUCT_SERVICE_BASE_URL = "http://localhost:8002/";
    public static final String PRODUCTS_ENDPOINT = "products/";
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_ID = "products/{productId}";
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_NAME = "search_products/";

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON).build();


    public ProductModel createProduct(ProductModel request, String accessToken) {
        Response response = createProductWithResponse(request, accessToken);
        return validaResponse(response, ProductModel.class);
    }

    public Response createProductWithResponse(ProductModel request, String accessToken) {
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

    public ProductModel updateProductIsAvailable(ProductPatchModel request, String productId, String accessToken) {
        Response response = updateProductWithResponse(request, productId, accessToken);
        return validaResponse(response, ProductModel.class);
    }

    public ProductModel getProduct(String productId, String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
        )
                .as(ProductModel.class);
    }

    public Response retrievingProducts(String accessToken) {
        return sendRequestGet(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken));
    }

    public ProductModel updateProductById(ProductModel request, String productId, String accessToken) {
        return sendRequestPut(RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request)).as(ProductModel.class);
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
