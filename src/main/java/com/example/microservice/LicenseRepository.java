package com.example.microservice;

import com.example.microservice.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {
    public List<License> findByOrganisationId(String organisationId);
    public License findByOrganizationIdAndLicenseId(String organisationId, String licenseId);

}
