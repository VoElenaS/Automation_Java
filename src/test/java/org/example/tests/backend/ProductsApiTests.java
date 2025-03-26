package org.example.tests.backend;

import org.example.backend.models.ProductCreateModel;
import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsApiTests extends BaseTest {

    private static String supplierId;

    @Test
    @Order(0)
    void createSupplier() {
        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel responseSupplier = suppliersServicesAPI.createSupplier(supplier, accessToken);
        supplierId = responseSupplier.getSupplierId();
        assertNotNull(supplierId, "The supplier wasn't created");
    }

    @Test
    @Order(1)
    void createDefaultProduct() {
        ProductCreateModel product = ProductCreateModel.generate(supplierId);
        ProductCreateModel responseProduct = productsServicesAPI.createProduct(product, accessToken);
        assertNotNull(responseProduct.getProductId(), "The product wasn't created");
    }



    @Test
    void creteProductWillAllFields() {

        ProductCreateModel product = ProductCreateModel.generate(supplierId);
        ProductCreateModel responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

}
