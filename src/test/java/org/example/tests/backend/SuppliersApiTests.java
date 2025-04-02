package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.models.SupplierModel;
import org.example.models.response.SupplierDetailModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SuppliersApiTests extends BaseTest {

    @Test
    void createSupplierWithAllFields() {
        SupplierModel supplier = SupplierModel.generate();
        SupplierModel response = suppliersServicesAPI.createSupplier(supplier, accessToken);

        assertNotNull(response.getSupplierId(), "Supplier is not created");
    }

    @Test
    void duplicateSupplierName() {
        SupplierModel supplier = SupplierModel.generate();
        SupplierModel response = suppliersServicesAPI.createSupplier(supplier, accessToken);

        Response responseDuplicateName = suppliersServicesAPI.createSupplierWithResponse(supplier, accessToken);

        assertEquals(responseDuplicateName.statusCode(), 422, "The duplicated supplier was created");
        assertEquals(responseDuplicateName.as(SupplierDetailModel.class).getDetail(), "This supplier is already existed", "The details doesn't match");
    }

    @Test
    void createSupplierWithOnlyMandatoryField() {
        SupplierModel supplierOnlyMandatoryField = SupplierModel.generateOnlyMandatoryFields();
        SupplierModel response = suppliersServicesAPI.createSupplier(supplierOnlyMandatoryField, accessToken);

        assertNotNull(response.getSupplierId(), "Supplier is not created");
    }

    @Test
    void retrievingSuppliers() {
        Response response = suppliersServicesAPI.retrievingSuppliers(accessToken);

        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody(), "Expected a non-null response body");
        List<Map<String, Object>> suppliers = response.jsonPath().getList("$");
        assertNotNull(suppliers, "Suppliers list shouldn't be null");

        if (suppliers.isEmpty()) {
            System.out.println("The list is [] - it's expected the DB is empty ");
        }

        Set<String> supplierIds = new HashSet<>();
        Set<String> supplierNames = new HashSet<>();

        for (Map<String, Object> supplier : suppliers) {
            String supplierId = (String) supplier.get("supplier_id");
            String supplierName = ((String) supplier.get("name")).toLowerCase();

            assertFalse(supplierIds.contains(supplierId), "Duplicate supplier ID found " + supplierId);
            assertFalse(supplierNames.contains(supplierName), "Duplicate supplier name found " + supplierName);
            assertNotNull(supplierId, "Supplier_Id Shouldn't be null");
            assertTrue(supplier.containsKey("name"), "The name is mandatory field");
            assertNotNull(supplier.get("name"), "The name field should not be null");
            assertTrue(supplier.containsKey("contact_name"), "The contact_name is mandatory field");
            assertNotNull(supplier.get("contact_name"), "The contact_name field should not be null");
            assertNotNull(supplier.get("contact_email"), "The contact_email field should not be null");
            assertTrue(supplier.containsKey("contact_email"), "The contact_email is mandatory field");
            assertTrue(supplier.containsKey("supplier_id"), "Each supplier should have a 'supplier_id'");

            supplierIds.add(supplierId);
            supplierNames.add(supplierName);
        }
    }
}
