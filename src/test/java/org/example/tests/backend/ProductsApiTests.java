package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.backend.models.SupplierCreateModel;
import org.example.backend.models.SupplierDetailModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductsApiTests extends BaseTest {

    @Test
    void createSupplier() {

        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel response = productsServiceAPI.createSupplier(supplier, accessToken);

        assertNotNull(response.getSupplierId(), "Supplier is not created");

    }

    @Test
    void duplicateSupplierName() {

        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel response = productsServiceAPI.createSupplier(supplier, accessToken);

        Response responseDuplicateName = productsServiceAPI.createSupplierWithResponse(supplier, accessToken);

        assertEquals(responseDuplicateName.statusCode(), 422, "The duplicated supplier was created");
        assertEquals(responseDuplicateName.as(SupplierDetailModel.class).getDetail(), "This supplier is already existed", "The details doesn't match");


    }
}
