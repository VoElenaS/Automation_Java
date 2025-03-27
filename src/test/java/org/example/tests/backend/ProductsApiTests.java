package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.backend.models.PatchProductModel;
import org.example.backend.models.ProductModel;
import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsApiTests extends BaseTest {

    private static String supplierId;
    private static String productId;

    @Test
    @Order(0)
    void createSupplier() {
        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel responseSupplier = suppliersServicesAPI.createSupplier(supplier, accessToken);
        assertNotNull(responseSupplier.getSupplierId(), "The supplier wasn't created");
        supplierId = responseSupplier.getSupplierId();
    }

    @Test
    @Order(1)
    void createDefaultProduct() {
        ProductModel product = ProductModel.generate(supplierId);
        ProductModel responseProduct = productsServicesAPI.createProduct(product, accessToken);
        assertNotNull(responseProduct.getProductId(), "The product wasn't created");
        productId = responseProduct.getProductId();
    }

    @Test
    void creteProductWillAllFields() {

        ProductModel product = ProductModel.generate(supplierId);
        ProductModel responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    void updateProductWillAllFields() {
        ProductModel existingProduct = productsServicesAPI.getProduct(productId, accessToken);
        PatchProductModel patchRequest = PatchProductModel.builder()
                .isAvailable(!existingProduct.isAvailable())
                .build();
        productsServicesAPI.updateProduct(patchRequest, productId, accessToken);
        ProductModel updatedProduct = productsServicesAPI.getProduct(productId, accessToken);

        assertNotEquals(existingProduct.isAvailable(), updatedProduct.isAvailable(), "The product wasn't updated");
        assertEquals(existingProduct.getProductId(), updatedProduct.getProductId(), "The product id was changed");
        assertEquals(existingProduct.getName(), updatedProduct.getName(), "The name of the product was updated");
        assertEquals(existingProduct.getSupplierId(), updatedProduct.getSupplierId(), "The supplier was updated for the product");
        assertEquals(existingProduct.getPrice(), updatedProduct.getPrice(), "The price was updated for the product");
        assertEquals(existingProduct.getStockQuantity(), updatedProduct.getStockQuantity(), "the quantity was updated for the product");
    }

    @Test
    void getProductById() {
        ProductModel product = productsServicesAPI.getProduct(productId, accessToken);
        assertNotNull(product.getProductId(), "Product not available");
    }

    @Test
    void getAllProductsRegularUser() {
        Response response = productsServicesAPI.retrievingProducts(accessToken);

        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody(), "Expected a non-null response body");
        List<ProductModel> products = response.jsonPath().getList("$", ProductModel.class);
        assertNotNull(products, "Products list shouldn't be NULL");

        if (products.isEmpty()) {
            System.out.println("The list is [] - it's expected, the DB is empty");
        }


    }

}
