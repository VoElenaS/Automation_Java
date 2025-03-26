package org.example.tests.backend;

import org.example.backend.models.ProductCreateModel;
import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Map;

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
        ProductCreateModel product = ProductCreateModel.generate(supplierId);
        ProductCreateModel responseProduct = productsServicesAPI.createProduct(product, accessToken);
        assertNotNull(responseProduct.getProductId(), "The product wasn't created");
        productId = responseProduct.getProductId();
    }



    @Test
    void creteProductWillAllFields() {

        ProductCreateModel product = ProductCreateModel.generate(supplierId);
        ProductCreateModel responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    void updateProductWillAllFields() {

        ProductCreateModel newNameSupler = productsServicesAPI.updateProduct(Map.of("name", ProductCreateModel.generate(supplierId).getName()), productId, accessToken);

        assertNotNull(newNameSupler, "Product wasn't updated");
    }



}
