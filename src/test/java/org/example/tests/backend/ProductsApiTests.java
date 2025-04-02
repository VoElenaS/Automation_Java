package org.example.tests.backend;

import io.restassured.response.Response;
import org.example.models.ProductModel;
import org.example.models.SupplierModel;
import org.example.models.request.ProductPatchModel;
import org.example.models.response.ValidationResponse;
import org.example.services.ProductsServicesAPI;
import org.example.tests.BaseTest;
import org.example.tests.frontend.models.ProductDataGenerator;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsApiTests extends BaseTest {

    private static String supplierId;
    private static String productId;

    @Test
    @Order(0)
    void createSupplier() {
        SupplierModel supplier = SupplierModel.generate();
        SupplierModel responseSupplier = suppliersServicesAPI.createSupplier(supplier, accessToken);
        assertNotNull(responseSupplier.getSupplierId(), "The supplier wasn't created");
        supplierId = responseSupplier.getSupplierId();
    }

    @Test
    @Order(1)
    void createDefaultProduct() {
        ProductModel product = ProductModel.generate(supplierId);
        ProductModel responseProduct = productsServicesAPI.createProduct(product, accessToken);
        assertNotNull(responseProduct.getProductId(), "The product wasn't created");
        productId = responseProduct.getProductId();
    }

    @Test
    void createProductWillAllFields() {
        ProductModel product = ProductModel.generate(supplierId);
        ProductModel responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    @Disabled("This test is failing, needs a fix")
    void createProductWillMandatoryFields() {
        ProductModel product = ProductModel.generateOnlyMandatoryFields(supplierId);
        ProductModel responseProduct = productsServicesAPI.createProduct(product, accessToken);

        assertNotNull(responseProduct.getSupplierId(), "Product is not created");
    }

    @Test
    void createProductWithShortName() {
        ProductModel product = ProductModel.generate(supplierId);
        product.setName(product.getName().substring(2, 4));
        Response response = productsServicesAPI.createProductWithResponse(product, accessToken);
        assertEquals(422, response.statusCode());
        ValidationResponse validationResponse = response.as(ValidationResponse.class);
        assertTrue(validationResponse.getDetail().stream().anyMatch(d -> d.getLoc().contains("name")), "There is validation error");
    }

    @Test
    void createProductWithWrongFormatPrice() {
        ProductModel product = ProductModel.generate(supplierId);
        product.setPrice(product.getPrice() + "12");
        Response response = productsServicesAPI.createProductWithResponse(product, accessToken);
        assertEquals(422, response.statusCode());
        ValidationResponse validationResponse = response.as(ValidationResponse.class);
        assertTrue(validationResponse.getDetail().stream().anyMatch(d -> d.getLoc().contains("price")), "There is problem with validation");
    }

    @Test
    void updateProductIsAvailable() {
        ProductModel existingProduct = productsServicesAPI.getProduct(productId, accessToken);
        ProductPatchModel patchRequest = ProductPatchModel.builder()
                .isAvailable(!existingProduct.isAvailable())
                .build();
        productsServicesAPI.updateProductIsAvailable(patchRequest, productId, accessToken);
        ProductModel updatedProduct = productsServicesAPI.getProduct(productId, accessToken);

        assertNotEquals(existingProduct.isAvailable(), updatedProduct.isAvailable(), "The product wasn't updated");
        assertEquals(existingProduct.getProductId(), updatedProduct.getProductId(), "The product id was changed");
        assertEquals(existingProduct.getName(), updatedProduct.getName(), "The name of the product was updated");
        assertEquals(existingProduct.getSupplierId(), updatedProduct.getSupplierId(), "The supplier was updated for the product");
        assertEquals(existingProduct.getPrice(), updatedProduct.getPrice(), "The price was updated for the product");
        assertEquals(existingProduct.getStockQuantity(), updatedProduct.getStockQuantity(), "the quantity was updated for the product");
    }

    @Test
    void updateProductById() {
        ProductModel product = productsServicesAPI.getProduct(productId, accessToken);
        ProductModel updatedProduct = ProductModel.builder()
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
        ProductModel response = productsServicesAPI.updateProductById(updatedProduct, product.getProductId(), accessToken);

        assertNotEquals(product.getName(), response.getName());
        assertNotEquals(product.getStockQuantity(), response.getStockQuantity());
        assertNotEquals(product.getPrice(), response.getPrice());
        assertNotEquals(product.getCategory(), response.getCategory());
    }

    @Test
    void getProductById() {
        ProductModel product = productsServicesAPI.getProduct(productId, accessToken);
        assertNotNull(product.getProductId(), "Product not available");
    }

    @Test
    void getAllProducts() {
        Response response = productsServicesAPI.retrievingProducts(accessToken);

        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody(), "Expected a non-null response body");
        List<ProductModel> products = response.jsonPath().getList("$", ProductModel.class);
        assertNotNull(products, "Products list shouldn't be NULL");

        if (products.isEmpty()) {
            System.out.println("The list is [] - it's expected, the DB is empty");
        }

        Set<String> productNames = new HashSet<>();
        for (ProductModel product : products) {
            String productName = product.getName().toLowerCase();
            boolean containsDuplicate = productNames.contains(productName);

            assertFalse(containsDuplicate, "Duplicted name was found " + productName);
            assertNotNull(product.getName(), "The name shouldn't be empty");
            assertNotNull(product.getSupplierId(), "The supplier shouldn't be empty");
            assertNotNull(product.getPrice(), "The price shouldn't be empty");
            assertNotNull(product.getStockQuantity(), "The Stock quantity shouldn't be empty");

            productNames.add(productName);
        }
    }

    @Test
    void deleteProduct() {
        ProductModel product = ProductModel.generate(supplierId);
        ProductsServicesAPI productsService = new ProductsServicesAPI();
        ProductModel createdProduct = productsService.createProduct(product, accessToken);
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

    @AfterAll
    static void tearDown() {
        ProductsServicesAPI productsServices = new ProductsServicesAPI();
    }
}
