package com.pivotal.cf.broker.service.helloworld;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceBindingDoesNotExistException;
import com.pivotal.cf.broker.exception.ServiceInstanceBindingExistsException;
import com.pivotal.cf.broker.exception.ServiceInstanceDoesNotExistException;
import com.pivotal.cf.broker.model.ServiceInstanceBinding;
import com.pivotal.cf.broker.service.ServiceInstanceBindingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pivotal.cf.broker.model.Authorization;
import com.pivotal.cf.broker.config.Env;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class HelloWorldServiceInstanceBindingService implements ServiceInstanceBindingService {

    private static final int PWD_LEN = 5;

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServiceInstanceBindingService.class);

    @Autowired
    private HelloWorldServiceMediator service;

    // expected to be set via env vars

    public HelloWorldServiceInstanceBindingService() {
    }

    public HelloWorldServiceInstanceBindingService(HelloWorldServiceMediator service) {
        this.service = service;
    }

    @Override
    public ServiceInstanceBinding createServiceInstanceBinding(String bindingId, String serviceInstanceId, String serviceId, String planId, String appGuid)
            throws ServiceInstanceDoesNotExistException, ServiceInstanceBindingExistsException, ServiceBrokerException {
        String username = pickUsername(bindingId);
        String password = pickPassword(PWD_LEN);
        String host = Env.serviceHost;
        String port = Env.servicePort;

        // Note: the shape/pattern of the uri is know to the broker to be supported by the service backend
        // (compare this to, e.g., the look and feel of a JDBC uri a MySQL broker supplies back to the CC
        String uriPattern = "http://%s:%s@%s:%s/helloworld/%s";
        String uri = String.format(uriPattern, username, password, host, port, serviceInstanceId);

        service.createAccount(username, password);
        service.createAuthorization(bindingId, pickUsername(bindingId), serviceInstanceId);

        Map<String,Object> credentials = new HashMap<String,Object>();
        credentials.put("username", username);
        credentials.put("password", password);
        credentials.put("host", host);
        credentials.put("port", port);
        credentials.put("uri", uri);

        ServiceInstanceBinding binding = new ServiceInstanceBinding(bindingId, serviceInstanceId, credentials, null, appGuid);
        return binding;
    }

    @Override
    public String deleteServiceInstanceBinding(String bindingId)
            throws ServiceBrokerException, ServiceInstanceBindingDoesNotExistException {
        String result = null;
        if(serviceInstanceBindingExists(bindingId)) {
            Authorization authorization = service.getAuthorization(bindingId);
            logger.info("deleteServiceInstanceBinding FOUND: " + authorization.toString());
            service.destroyAuthorization(authorization.getId());
            service.destroyAccount(authorization.getAccountId());
            result = bindingId;
        }
        return result;
    }

    @Override
    public boolean isServiceInstanceBound(String serviceInstanceId) {
         return false; // TODO
    }

    @Override
    public boolean serviceInstanceBindingExists(String bindingId) throws ServiceBrokerException {
        return service.getAuthorization(bindingId) != null;
    }


    // we pick a username...
    private String pickUsername(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    // we pick a password...
    private String pickPassword(int len) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, len);
    }
}
