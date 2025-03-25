package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tests.frontend.models.ProductDataGenerator;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductCreateModel {

    private String name; // Product name, required and should be 3-100 characters, only letters and numbers.
    private String description; // Optional. Product description up to 500 characters.
    private String category; // Optional. Product category up to 50 characters, only letters and numbers.
    private String price; // Required. Positive number with 2 decimals, max 10 digits including decimals.
    @JsonProperty("stock_quantity")
    private int stockQuantity; // Required. Whole number, at least 0.
    @JsonProperty("supplier_id")
    private String supplierId; // Required. Valid UUID, should match an existing supplier ID in the database.
    @JsonProperty("is_available")
    private boolean isAvailable; // Optional. Boolean flag to indicate availability.
    @JsonProperty("created_at")
    private String createdAt; // Required. Timestamp when the product was created.
    @JsonProperty("updated_at")
    private String updatedAt; // Required. Timestamp when the product was last updated.
    @JsonProperty("image_url")
    private String imageUrl; // Optional. URL of the product image, max 255 characters, supports png, jpeg, jpg.
    private String weight; // Optional. Positive number with a decimal point. Max 6 digits (2 decimals).
    private String dimensions; // Optional. Max 100 characters. Format: whole numbers with "x" symbol allowed.
    private String manufacturer; // Optional. Max 100 characters, only letters and numbers.


    public static ProductCreateModel generate(String supplierId) {
        return ProductCreateModel.builder()
                .name(ProductDataGenerator.generateName())
                .description(ProductDataGenerator.generateDescription())
                .category(ProductDataGenerator.generateCategory())
                .price(ProductDataGenerator.generatePrice())
                .stockQuantity(ProductDataGenerator.generateStockQuantity())
                .supplierId(supplierId) // Ensuring supplierId is provided.
                .isAvailable(ProductDataGenerator.generateIsAvailable())
                .imageUrl(ProductDataGenerator.generateImageUrl())
                .weight(ProductDataGenerator.generateWeight())
                .dimensions(ProductDataGenerator.generateDimensions())
                .manufacturer(ProductDataGenerator.generateManufacturer())
                .build();
    }
}