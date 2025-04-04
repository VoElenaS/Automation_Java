package org.example.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductRequest {
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