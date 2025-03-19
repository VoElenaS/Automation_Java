package org.example.tests.backend;

import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductsApiTests extends BaseTest {

    @Test
    void createSupplier() {

        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel response = productsServiceAPI.createSupplier(supplier, accessToken);

        assertNotNull(response.getSupplierId(), "Supplier is not created");

    }
}
