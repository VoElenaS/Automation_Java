package org.example.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.tests.frontend.models.SupplierDataGenerator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(exclude = {"supplierId"})

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
    @JsonProperty("supplier_id")
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

    public static SupplierCreateModel generateOnlyMandatoryFields() {
        return SupplierCreateModel.builder()
                .name(SupplierDataGenerator.generateName())
                .contactName(SupplierDataGenerator.generateContactName())
                .contactEmail(SupplierDataGenerator.generateContactEmail())
                .build();
    }
}