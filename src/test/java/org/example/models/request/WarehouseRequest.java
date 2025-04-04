package org.example.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class WarehouseRequest {
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

}