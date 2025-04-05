package org.example.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

public class ProductsInWarehouseResponse {
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("warehouse_id")
    private String warehouseId;
    private int quantity;
    @JsonProperty("product_warehouse_id")
    private String productWarehouseId;
}

