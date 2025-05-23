package org.example.tests.backend;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import jakarta.validation.ConstraintViolation;
import org.example.models.generators.ProductDataGenerator;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.request.ProductPatchModel;
import org.example.models.request.ProductRequest;
import org.example.models.request.SupplierRequest;
import org.example.models.response.ProductResponse;
import org.example.models.response.SupplierResponse;
import org.example.models.response.ValidationResponse;
import org.example.services.ProductsServicesAPI;
import org.example.tests.BaseApiTest;
import org.example.utils.ValidationUtils;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Product management")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsApiTests extends BaseApiTest {

    private static String supplierId;
    private static String productId;

    @Test
    @Order(0)
    void createSupplier() {
        SupplierRequest supplier = SupplierDataGenerator.generate();
        SupplierResponse responseSupplier = suppliersServicesAPI.createSupplier(supplier, accessToken);
        assertNotNull(responseSupplier.getSupplierId(), "The supplier wasn't created");
        supplierId = responseSupplier.getSupplierId();
    }

    @Test
    @Order(1)
    void createDefaultProduct() {
        ProductRequest product = ProductDataGenerator.generate(supplierId);
        ProductResponse responseProduct = productsServicesAPI.createProduct(product, accessToken);
        assertNotNull(responseProduct.getProductId(), "The product wasn't created");
        productId = responseProduct.getProductId();
    }

    @Test
    void createProductWillAllFields() {
        ProductRequest product = ProductDataGenerator.generate(supplierId);
        ProductResponse responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @RepeatedTest(50)
    void createProductWillMandatoryFields() {
        SupplierRequest supplier = SupplierDataGenerator.generate();
        SupplierResponse responseSupplier = suppliersServicesAPI.createSupplier(supplier, accessToken);
        assertNotNull(responseSupplier.getSupplierId(), "The supplier wasn't created");
        supplierId = responseSupplier.getSupplierId();
        ProductRequest product = ProductDataGenerator.generateOnlyMandatoryFields(supplierId);
        ProductResponse responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    void createProductWithShortName() {
        ProductRequest product = ProductDataGenerator.generate(supplierId);
        product.setName(product.getName().substring(2, 4));
        Response response = productsServicesAPI.createProductWithResponse(product, accessToken);
        Set<ConstraintViolation<ProductRequest>> validate = ValidationUtils.getValidator().validate(product);
        assertFalse(validate.isEmpty(), "Expected a validation error for name length, but none was found.");
        assertEquals(422, response.statusCode());
        ValidationResponse validationResponse = response.as(ValidationResponse.class);
        assertTrue(validationResponse.getDetail().stream().anyMatch(d -> d.getLoc().contains("name")), "There is an error of validation");
    }

    @Test
    void createProductWithWrongFormatPrice() {
        ProductRequest product = ProductDataGenerator.generate(supplierId);
        product.setPrice(product.getPrice() + "12");
        Response response = productsServicesAPI.createProductWithResponse(product, accessToken);
        assertEquals(422, response.statusCode());
        ValidationResponse validationResponse = response.as(ValidationResponse.class);
        assertTrue(validationResponse.getDetail().stream().anyMatch(d -> d.getLoc().contains("price")), "There is problem with validation");
    }

    @Test
    void updateProductIsAvailable() {
        ProductResponse existingProduct = productsServicesAPI.getProduct(productId, accessToken);
        ProductPatchModel patchRequest = ProductPatchModel.builder()
                .isAvailable(!existingProduct.isAvailable())
                .build();
        productsServicesAPI.updateProductIsAvailable(patchRequest, productId, accessToken);
        ProductResponse updatedProduct = productsServicesAPI.getProduct(productId, accessToken);

        assertNotEquals(existingProduct.isAvailable(), updatedProduct.isAvailable(), "The product wasn't updated");
        assertEquals(existingProduct.getProductId(), updatedProduct.getProductId(), "The product id was changed");
        assertEquals(existingProduct.getName(), updatedProduct.getName(), "The name of the product was updated");
        assertEquals(existingProduct.getSupplierId(), updatedProduct.getSupplierId(), "The supplier was updated for the product");
        assertEquals(existingProduct.getPrice(), updatedProduct.getPrice(), "The price was updated for the product");
        assertEquals(existingProduct.getStockQuantity(), updatedProduct.getStockQuantity(), "the quantity was updated for the product");
    }

    @Test
    void updateProductById() {
        ProductResponse product = productsServicesAPI.getProduct(productId, accessToken);
        ProductRequest updatedProduct = ProductRequest.builder()
                .name(ProductDataGenerator.generateName())
                .stockQuantity(ProductDataGenerator.generateStockQuantity())
                .dimensions(product.getDimensions())
                .price(ProductDataGenerator.generatePrice())
                .manufacturer(product.getManufacturer())
                .weight(product.getWeight())
                .category(ProductDataGenerator.generateCategory())
                .description(product.getDescription())
                .imageUrl(product.getImageUrl())
                .supplierId(product.getSupplierId())
                .isAvailable(true)
                .build();
        ProductResponse response = productsServicesAPI.updateProductById(updatedProduct, product.getProductId(), accessToken);

        assertNotEquals(product.getName(), response.getName());
        assertNotEquals(product.getStockQuantity(), response.getStockQuantity());
        assertNotEquals(product.getPrice(), response.getPrice());
        assertNotEquals(product.getCategory(), response.getCategory());
    }

    @Test
    void getProductById() {
        ProductResponse product = productsServicesAPI.getProduct(productId, accessToken);
        assertNotNull(product.getProductId(), "Product not available");
    }

    @Test
    void getAllProducts() {
        Response response = productsServicesAPI.retrievingProducts(accessTokenSuperAdmin);

        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody(), "Expected a non-null response body");
        List<ProductRequest> products = response.jsonPath().getList("$", ProductRequest.class);
        assertNotNull(products, "Products list shouldn't be NULL");

        if (products.isEmpty()) {
            System.out.println("The list is [] - it's expected, the DB is empty");
        }

        Set<String> productNames = new HashSet<>();
        for (ProductRequest product : products) {
            Set<ConstraintViolation<ProductRequest>> violations = ValidationUtils.getValidator().validate(product);
            assertTrue(violations.isEmpty(), "Validation error: " + violations);

            assertTrue(productNames.add(product.getName().toLowerCase()), "Duplicated name was found " + product.getName());
        }
    }

    @Test
    void deleteProduct() {
        ProductRequest product = ProductDataGenerator.generate(supplierId);
        ProductsServicesAPI productsService = new ProductsServicesAPI();
        ProductResponse createdProduct = productsService.createProduct(product, accessToken);
        Response response = productsServicesAPI.deleteProduct(createdProduct.getProductId(), accessToken);

        assertEquals(200, response.statusCode());
        assertEquals("Product deleted", response.jsonPath().getString("message"));
    }

    @Test
    void retrievingProductByName() {
        String name = productsServicesAPI.getProduct(productId, accessToken).getName().toLowerCase();
        Response response = productsServicesAPI.retrievingProductByName(name, accessToken);

        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody());
    }
}
