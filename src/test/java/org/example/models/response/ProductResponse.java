package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductResponse {

    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name must not be blank")
    private String name;

    private String description;

    private String category;

    @NotNull(message = "Price is required")
    @Pattern(regexp = "^(?!0+(\\.0{1,2})?$)\\d{1,8}(\\.\\d{1,2})?$", message = "Price must be positive with max 10 digits total and 2 decimals")
    private String price;

    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @NotNull(message = "Supplier ID is required")
    @JsonProperty("supplier_id")
    private String supplierId;

    @JsonProperty("is_available")
    private boolean isAvailable;

    @NotNull(message = "CreatedAt is required")
    @JsonProperty("created_at")
    private String createdAt;

    @NotNull(message = "UpdatedAt is required")
    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("image_url")
    private String imageUrl;

    private String weight;

    private String dimensions;

    private String manufacturer;

    @NotNull(message = "Product ID is required")
    @JsonProperty("product_id")
    private String productId;

    @NotNull(message = "User ID is required")
    @JsonProperty("user_id")
    private String userId;
}
