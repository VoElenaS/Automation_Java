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

public class WarehouseResponse {
    private String location;
    @JsonProperty("manager_name")
    private String managerName;
    private Integer capacity;
    @JsonProperty("current_stock")
    private Integer currentStock;
    @JsonProperty("contact_number")
    private String contactNumber;
    private String email;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("area_size")
    private String areaSize;
    @JsonProperty("warehouse_id")
    private String warehouseId;
}