package com.example.microservice.service;

import com.example.microservice.LicenseRepository;
import com.example.microservice.config.ServiceConfig;
import com.example.microservice.model.License;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final MessageSource messageSource;
    public License getLicense(String licenseId, String organizationId){
        License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (license==null){
            throw new IllegalArgumentException(
                    String.format(messageSource.getMessage(
                                    "license.search.error.message", null, null),
                            licenseId, organizationId));
        }
        return license.withComment(config.getProperty());
    }
    public License createLicense(License license){
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }
    public License updateLicense(License license){
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }
    public String deleteLicense(String licenseId){
        License license=licenseRepository.findById(licenseId).orElseThrow(() -> {throw new NoSuchElementException("No such element found");});
        licenseRepository.delete(license);
        String responseMessage = String.format(messageSource.getMessage(
                "license.delete.message", null, null),licenseId);
        return responseMessage;
    }

}
