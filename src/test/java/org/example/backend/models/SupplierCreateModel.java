package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tests.frontend.models.SupplierDataGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)

public class SupplierCreateModel {

    private String name;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("contact_email")
    private String contactEmail;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;
    private String country;
    private String city;
    private String website;
    @JsonIgnore  // This will prevent supplierId from appearing in JSON
    private String supplierId;

    public static SupplierCreateModel generate() {

        return SupplierCreateModel.builder()
                .name(SupplierDataGenerator.generateName())
                .contactName(SupplierDataGenerator.generateContactName())
                .contactEmail(SupplierDataGenerator.generateContactEmail())
                .phoneNumber(SupplierDataGenerator.generatePhoneNumber())
                .address(SupplierDataGenerator.generateAddress())
                .country(SupplierDataGenerator.generateCountry())
                .city(SupplierDataGenerator.generateCity())
                .website(SupplierDataGenerator.generateWebsite())
                .build();
    }
}
