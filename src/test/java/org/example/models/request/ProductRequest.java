package org.example.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductRequest {
    @NotNull(message = "Product name is required")
    @NotBlank(message = "Product name must not be blank")
    @Length(min = 4, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Name can only contain letters and numbers")
    private String name;
    private String description;
    private String category;
    private String price;
    @JsonProperty("stock_quantity")
    private int stockQuantity;
    @JsonProperty("supplier_id")
    private String supplierId;
    @JsonProperty("is_available")
    private boolean isAvailable;
    @JsonProperty("image_url")
    private String imageUrl;
    private String weight;
    private String dimensions;
    private String manufacturer;
}