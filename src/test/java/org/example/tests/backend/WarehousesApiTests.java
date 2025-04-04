package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.models.generators.WarehouseDataGenerator;
import org.example.models.request.WarehouseRequest;
import org.example.models.response.WarehouseResponse;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WarehousesApiTests extends BaseTest {

    @Test
    @DisplayName("Create Warehouse with All Fileds")
    void createWarehouse() {
        WarehouseRequest warehouse = WarehouseDataGenerator.generate();
        Response response = warehousesServicesAPI.createWarehouseWithResponse(warehouse, accessToken);
        assertEquals(200, response.statusCode());
    }

    @Test
    @DisplayName("Check for duplicate location")
    void createWarehouseWithDuplicateLocation() {
        WarehouseRequest warehouse = WarehouseDataGenerator.generate();
        WarehouseResponse response = warehousesServicesAPI.createWarehouse(warehouse, accessToken);
        Response warehouseDuplicate = warehousesServicesAPI.createWarehouseWithResponse(warehouse, accessToken);
        assertEquals(422, warehouseDuplicate.statusCode(), "The warehouse at the same location couldn't be created.");
    }

    @Test
    @DisplayName("Retrieving all Warehouses")
    void retrievingWarehouses() {
        Response response = warehousesServicesAPI.retrievingWarehouses(accessToken);

        assertEquals(200, response.statusCode(), "There is error in the process of retrieving");
        assertNotNull(response.getBody(), "Expected a non-null body");
        List<WarehouseResponse> warehouses = response.jsonPath().getList("$", WarehouseResponse.class);
        assertNotNull(warehouses, "The response is empty");

        if (warehouses.isEmpty()) {
            System.out.println("The list [] is expected in the first retrieval when no data exists in the database, before the warehouses are created.");
        }

        Set<String> warehouseIds = new HashSet<>();
        Set<String> locations = new HashSet<>();

        for (WarehouseResponse warehouse : warehouses) {
            String warehouseId = warehouse.getWarehouseId();
            String warehouselocation = warehouse.getLocation();

            assertFalse(locations.contains(warehouselocation), "There is duplicate " + warehouselocation);
            assertNotNull(warehouse.getCapacity(), "The mandatory field capacity should be filled");
            assertNotNull(warehouse.getIsActive(), "The mandatory filed IsActive should be filled automatically");
            assertNotNull(warehouse.getCurrentStock(), "The mandatory filed currentStock should be filled");
            assertNotNull(warehouse.getWarehouseId(), "The filed warehouseId should be filled");

            locations.add(warehouselocation);
            warehouseIds.add(warehouseId);
        }

    }
}
