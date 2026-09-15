package com.example.microservice.service.client;

import com.example.microservice.model.Organization;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {
    private DiscoveryClient discoveryClient;
    public Organization getOrganization(String organizationId){
        RestTemplate restTemplate=new RestTemplate();
        List<InstanceInfo> instances= discoveryClient.getInstancesById("organization-service");
        if (instances==null) return null;
        String serviceUri = String.format("%s/v1/organization/%s", instances.get(0).toString(), organizationId);
        ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET,null, Organization.class, organizationId );
        return restExchange.getBody();

    }
}
