package com.pivotal.cf.broker.service.helloworld;

import com.pivotal.cf.broker.model.Catalog;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HelloWorldCatalogService implements CatalogService {

    @Autowired
    private Catalog helloworldCatalog;

    private Map<String,ServiceDefinition> serviceDefs = new HashMap<String,ServiceDefinition>();

    @Override
    public Catalog getCatalog() {
        return helloworldCatalog;
    }

    @Override
    public ServiceDefinition getServiceDefinition(String serviceId) {
        if(serviceDefs.isEmpty()) {
            initializeMap();
        }
        return serviceDefs.get(serviceId);
    }

    public HelloWorldCatalogService() {
    }

    public HelloWorldCatalogService(Catalog helloworldCatalog) {
        this.helloworldCatalog = helloworldCatalog;
        initializeMap();
    }

    private void initializeMap() {
        for (ServiceDefinition def: helloworldCatalog.getServiceDefinitions()) {
            serviceDefs.put(def.getId(), def);
        }
    }


}
