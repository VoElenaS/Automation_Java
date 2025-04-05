package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.models.generators.ProductDataGenerator;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.generators.WarehouseDataGenerator;
import org.example.models.request.ProductRequest;
import org.example.models.request.SupplierRequest;
import org.example.models.request.WarehouseRequest;
import org.example.models.response.ProductResponse;
import org.example.models.response.SupplierResponse;
import org.example.models.response.WarehouseResponse;
import org.example.tests.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsInWarehouseApiTests extends BaseTest {

    @Test
    @DisplayName("Adding product in warehouse")
    void AddProductInWarehouse() {
        WarehouseRequest warehouse = WarehouseDataGenerator.generateOnlyMandatoryFields();
        SupplierRequest supplier = SupplierDataGenerator.generateOnlyMandatoryFields();
        SupplierResponse supplierResponse = suppliersServicesAPI.createSupplier(supplier, accessToken);
        ProductRequest product = ProductDataGenerator.generateOnlyMandatoryFields(supplierResponse.getSupplierId());
        ProductResponse productResponse = productsServicesAPI.createProduct(product, accessToken);
        WarehouseResponse warehouseResponse = warehousesServicesAPI.createWarehouse(warehouse, accessToken);

        int stockQuantity = productResponse.getStockQuantity();

        Response response = warehousesServicesAPI
                .addProductInWarehouse(accessToken, warehouseResponse.getWarehouseId(), productResponse.getProductId(), stockQuantity);

        assertEquals(200, response.statusCode(), "There is an error during adding the product in warehouse");


    }
}
