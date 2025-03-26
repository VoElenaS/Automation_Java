package org.example.tests.backend;

import org.example.backend.models.PatchProductModel;
import org.example.backend.models.ProductModel;
import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        PatchProductModel patchRequest = PatchProductModel.builder().isAvailable(!existingProduct.isAvailable()).build();

        ProductModel updatedProduct = productsServicesAPI.updateProduct(patchRequest, productId, accessToken);


        assertNotEquals(existingProduct.isAvailable(), updatedProduct.isAvailable(), "The product wasn't updated");
    }



}
