package org.example.tests.backend;

import io.qameta.allure.Feature;
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
import org.example.tests.BaseApiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Product in Warehouse management")
public class ProductsInWarehouseApiTests extends BaseApiTest {

    @Test
    @DisplayName("Adding product in warehouse")
    void addProductInWarehouse() {
        WarehouseRequest warehouse = WarehouseDataGenerator.generateOnlyMandatoryFields();
        SupplierRequest supplier = SupplierDataGenerator.generateOnlyMandatoryFields();
        SupplierResponse supplierResponse = suppliersServicesAPI.createSupplier(supplier, accessToken);
        ProductRequest product = ProductDataGenerator.generateOnlyMandatoryFields(supplierResponse.getSupplierId());
        ProductResponse productResponse = productsServicesAPI.createProduct(product, accessToken);
        WarehouseResponse warehouseResponse = warehousesServicesAPI.createWarehouse(warehouse, accessToken);
        int stockQuantity = productResponse.getStockQuantity();
        Response response = warehousesServicesAPI
                .addProductInWarehouseWithResponse(accessToken, warehouseResponse.getWarehouseId(), productResponse.getProductId(), stockQuantity);
        assertEquals(200, response.statusCode(), "There is an error during adding the product in warehouse");
    }

    @Test
    @DisplayName("Bulk Add & Quantity Check")
    void addProductsInWarehouse() {
        WarehouseRequest warehouse = WarehouseDataGenerator.generateOnlyMandatoryFields();
        WarehouseResponse warehouseResponse = warehousesServicesAPI.createWarehouse(warehouse, accessToken);
        SupplierRequest supplier = SupplierDataGenerator.generateOnlyMandatoryFields();
        SupplierResponse supplierResponse = suppliersServicesAPI.createSupplier(supplier, accessToken);

        int totalProductsQuantityWh = IntStream.range(0, 3)
                .mapToObj(d -> ProductDataGenerator.generateOnlyMandatoryFields(supplierResponse.getSupplierId()))
                .map(p -> productsServicesAPI.createProduct(p, accessToken))
                .map(p -> warehousesServicesAPI.addProductInWarehouse(accessToken, warehouseResponse.getWarehouseId(), p.getProductId(), p.getStockQuantity()))
                .mapToInt(p -> p.getQuantity())
                .sum();

        Integer currentStock = warehousesServicesAPI.retrievingWarehousesById(warehouseResponse.getWarehouseId(), accessToken).getCurrentStock();

        assertEquals(warehouseResponse.getCurrentStock() + totalProductsQuantityWh, currentStock, "The total current stock is wrong");
    }


}
