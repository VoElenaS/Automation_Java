package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.models.generators.WarehouseDataGenerator;
import org.example.models.request.WarehouseRequest;
import org.example.models.response.WarehouseResponse;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//    @Test
//    @DisplayName("Retrieving all Warehouses")
//    void retrievingWarehouses() {
//        Response response = warehousesServicesAPI.retrievingWarehouses(accessToken);
//
//        assertEquals(200, response.statusCode(), "There is error in the process of retrieving");
//        assertNotNull(response.getBody(), "Expected a non-null body");
//        List<Map<String, Object>> warehouses = response.jsonPath().getList("$");
//        assertNotNull(warehouses, "The response is empty");
//
//        if(warehouses.isEmpty()) {
//            System.out.println("The list [] is expected in the first retrieval when no data exists in the database, before the warehouses are created.");
//        }
//
//        Set<String> warehouseId = new HashSet<>();
//        Set<String> location = new HashSet<>();
//
//        for (Map<String, Object> warehouse: warehouses) {
//
//
//
//        }
//
//    }
}
