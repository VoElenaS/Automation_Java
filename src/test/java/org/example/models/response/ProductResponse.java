package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ProductResponse {

    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name must not be blank")
    @Length(min = 3, max = 100,
            message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",
            message = "Name can only contain letters and numbers")
    private String name;

    @Length(max = 500, message = "Description can be up to 500 characters")
    private String description;

    @Length(max = 50, message = "Category can be up to 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "Category can only contain letters and numbers")
    private String category;

    @NotNull(message = "Price is required")
    @Pattern(regexp = "^(?!0+(\\.0{1,2})?$)\\d{1,8}(\\.\\d{1,2})?$",
            message = "Price must be positive with max 10 digits total and 2 decimals")
    private String price;

    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @NotNull(message = "Supplier ID is required")
    @UUID(message = "Supplier ID must be a valid UUID")
    @JsonProperty("supplier_id")
    private String supplierId;

    @JsonProperty("is_available")
    private boolean isAvailable;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @NotNull(message = "CreatedAt is required")
    @JsonProperty("created_at")
    private String createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    @NotNull(message = "UpdatedAt is required")
    @JsonProperty("updated_at")
    private String updatedAt;

    @Length(max = 255, message = "Image URL can be up to 255 characters")
    @Pattern(regexp = ".*\\.(?i)(png|jpe?g)$",
            message = "Image URL must end with .png, .jpg, or .jpeg")
    @JsonProperty("image_url")
    private String imageUrl;

    @Pattern(regexp = "^\\d{1,4}(\\.\\d{1,2})?$",
            message = "Weight must be a positive number with up to 6 digits including 2 decimals")
    private String weight;

    @Length(max = 100, message = "Dimensions can be up to 100 characters")
    @Pattern(regexp = "^[0-9xX]+$",
            message = "Dimensions can only contain digits and 'x'")
    private String dimensions;

    @Length(max = 100, message = "Manufacturer can be up to 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]*$",
            message = "Manufacturer can only contain letters and numbers")
    private String manufacturer;

    @NotNull(message = "Product ID is required")
    @UUID(message = "Product ID must be a valid UUID")
    @JsonProperty("product_id")
    private String productId;

    @NotNull(message = "User ID is required")
    @UUID(message = "User ID must be a valid UUID")
    @JsonProperty("user_id")
    private String userId;
}