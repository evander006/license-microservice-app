package com.example.microservice.controller;

import com.example.microservice.model.License;
import com.example.microservice.service.LicenseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
    public LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }


    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("licenseId") String licenseId,@PathVariable("organizationId") String organizationId){
        var license=licenseService.getLicense(licenseId, organizationId);
        return ResponseEntity.status(HttpStatus.OK).body(license);
    }
    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license){
        var licenseUpdated=licenseService.updateLicense(license);
        return ResponseEntity.status(HttpStatus.OK).body(licenseUpdated);
    }
    @PostMapping
    public ResponseEntity<License> postLicense(@RequestBody License license){
        var licenseToCreate=licenseService.createLicense(license);
        return ResponseEntity.status(HttpStatus.CREATED).body(licenseToCreate);
    }
    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> postLicense(@PathVariable("licenseId") String licenseId){
        var licenseToDelete=licenseService.deleteLicense(licenseId);
        return ResponseEntity.status(HttpStatus.OK).body(licenseToDelete);
    }
}
