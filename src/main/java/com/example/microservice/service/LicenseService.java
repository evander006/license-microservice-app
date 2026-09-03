package com.example.microservice.service;

import com.example.microservice.model.License;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LicenseService {
    public License getLicense(String id, String organizationId){
        License license = new License();
        license.setId(new Random().nextInt(1000));
        license.setLicenseId(id);
        license.setOrgId(organizationId);
        license.setDescr("Software product");
        license.setProductName("Ostock");
        license.setLicenseType("full");
        return license;
    }
    public String createLicense(License license, String orgId){
        String msg=null;
        if (license!=null){
            license.setOrgId(orgId);
            msg=String.format("This is the post and the object is: %s",
                    license);
        }
        return msg;
    }
    public String updateLicense(License license, String orgId){
        String responseMessage = null;
        if (license != null) {
            license.setOrgId(orgId);
            responseMessage = String.format(
                    "This is the put and the object is: %s", license);
        }
        return responseMessage;
    }
    public String deleteLicense(String id , String orgID){
        String responseMessage = null;
        responseMessage = String.format(
                "Deleting license with id %s for the organization %s",
                id, orgID);
        return responseMessage;

    }

}
