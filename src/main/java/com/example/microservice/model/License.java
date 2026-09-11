package com.example.microservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity(name = "licenses")
public class License {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "organization_id", nullable = false)
    private String organizationId;
    @Column(name = "product_name", nullable = false)
    private String productName;
    @Column(name = "license_type", nullable = false)
    private String licenseType;
    @Column(name="comment")
    private String comment;
    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
