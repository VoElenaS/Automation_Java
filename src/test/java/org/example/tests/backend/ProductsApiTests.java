package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.backend.models.ProductCreateModel;
import org.example.backend.models.SupplierCreateModel;
import org.example.backend.models.SupplierDetailModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsApiTests extends BaseTest {

    @Test
    void createSupplierWithAllFields() {
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

    @Test
    void createSupplierWithOnlyMandatoryField() {
        SupplierCreateModel supplierOnlyMandatoryField = SupplierCreateModel.generateOnlyMandatoryFields();
        SupplierCreateModel response = productsServiceAPI.createSupplier(supplierOnlyMandatoryField, accessToken);

        assertNotNull(response.getSupplierId(), "Supplier is not created");
    }

    @Test
    void creteProductWillAllFields() {
        SupplierCreateModel supplier = SupplierCreateModel.generate();
        SupplierCreateModel responseSupplier = productsServiceAPI.createSupplier(supplier, accessToken);

        String supplierId = responseSupplier.getSupplierId();

        ProductCreateModel product = ProductCreateModel.generate(supplierId);
        ProductCreateModel responseProduct = productsServiceAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    void retrievingSuppliers() {
        Response response = productsServiceAPI.retrievingSuppliers(accessToken);

        assertEquals(response.statusCode(), 200);
        assertNotNull(response.getBody(), "Suppliers list shouldn't be empty");
        List<Map<String, Object>> suppliers = response.jsonPath().getList("$");
        assertNotNull(suppliers, "Suppliers list shouldn't be null");

        if (suppliers.isEmpty()) {
            System.out.println("The list is [] - it's expected the DB is empty ");
            return;
        }

        Set<String> supplierIds = new HashSet<>();

        for (Map<String, Object> supplier : suppliers) {
            String supplierId = (String) supplier.get("supplier_id");

            assertFalse(supplierIds.contains(supplierId), "Duplicate supplier ID found " + supplierId);
            assertNotNull(supplierId, "Supplier_Id Shouldn't be null");
            assertTrue(supplier.containsKey("name"), "The name is mandatory field");
            assertNotNull(supplier.get("name"), "The name field should not be null");
            assertTrue(supplier.containsKey("contact_name"), "The contact_name is mandatory field");
            assertNotNull(supplier.get("contact_name"), "The contact_name field should not be null");
            assertNotNull(supplier.get("contact_email"), "The contact_email field should not be null");
            assertTrue(supplier.containsKey("contact_email"), "The contact_email is mandatory field");
            assertTrue(supplier.containsKey("supplier_id"), "Each supplier should have a 'supplier_id'");

            supplierIds.add(supplierId);

        }
    }
}
