package org.example.services;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.models.request.ProductPatchModel;
import org.example.models.request.ProductRequest;
import org.example.models.response.ProductResponse;
import org.example.utils.TestProperties;

public class ProductsServicesAPI extends BaseAPI {

    public static final String PRODUCT_SERVICE_BASE_URL = TestProperties.properties.getProperty("product_service_base_url");
    public static final String PRODUCTS_ENDPOINT = TestProperties.properties.getProperty("products_endpoint");
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_ID = TestProperties.properties.getProperty("products_endpoint_with_product_id");
    public static final String PRODUCTS_ENDPOINT_WITH_PRODUCT_NAME = TestProperties.properties.getProperty("products_endpoint_with_product_name");

    public static RequestSpecification baseRequest = new RequestSpecBuilder()
            .setBaseUri(PRODUCT_SERVICE_BASE_URL)
            .setContentType(ContentType.JSON).build();


    public ProductResponse createProduct(ProductRequest request, String accessToken) {
        Response response = createProductWithResponse(request, accessToken);
        return validaResponse(response, ProductResponse.class);
    }

    public Response createProductWithResponse(ProductRequest request, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken)
                .body(request);
        return sendRequestPost(specification);
    }

    public Response updateProductWithResponse(ProductPatchModel request, String productId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request);
        return sendRequestPatch(specification);
    }

    public ProductResponse updateProductIsAvailable(ProductPatchModel request, String productId, String accessToken) {
        Response response = updateProductWithResponse(request, productId, accessToken);
        return validaResponse(response, ProductResponse.class);
    }

    public ProductResponse getProduct(String productId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken);
        return validaResponse(sendRequestGet(specification), ProductResponse.class);
    }

    public Response retrievingProducts(String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT)
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestGet(specification);
    }

    public ProductResponse updateProductById(ProductRequest request, String productId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken)
                .body(request);
        return validaResponse(sendRequestPut(specification), ProductResponse.class);
    }

    public Response deleteProduct(String productId, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_ID)
                .pathParams("productId", productId)
                .header("Authorization", "Bearer " + accessToken);
        return sendRequestDelete(specification);
    }

    public Response retrievingProductByName(String name, String accessToken) {
        RequestSpecification specification = RestAssured.given(baseRequest)
                .basePath(PRODUCTS_ENDPOINT_WITH_PRODUCT_NAME)
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("name", name);
        return sendRequestGet(specification);
    }

}
