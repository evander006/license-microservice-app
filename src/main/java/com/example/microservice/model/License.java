package com.example.microservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class License {
    private int id;
    private String licenseId;
    private String descr;
    private String orgId;
    private String productName;
    private String licenseType;
}
