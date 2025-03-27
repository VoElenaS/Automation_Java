package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.backend.models.WarehouseModel;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarehousesApiTests extends BaseTest {

    @Test
    void createWarehouse() {
        WarehouseModel warehouse = WarehouseModel.generate();
        Response response = warehousesServicesAPI.createWarehouse(warehouse, accessToken);
        assertEquals(200, response.statusCode());

    }
}
