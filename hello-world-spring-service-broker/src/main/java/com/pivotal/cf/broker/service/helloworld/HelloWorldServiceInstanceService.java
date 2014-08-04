package com.pivotal.cf.broker.service.helloworld;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceDoesNotExistException;
import com.pivotal.cf.broker.exception.ServiceInstanceExistsException;
import com.pivotal.cf.broker.exception.ServiceInstanceIsBoundException;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.model.ServiceInstance;
import com.pivotal.cf.broker.service.ServiceInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldServiceInstanceService.class);

    @Autowired
    private HelloWorldServiceMediator service;

    public HelloWorldServiceInstanceService() {
    }

    public HelloWorldServiceInstanceService(HelloWorldServiceMediator service) {
        this.service = service;
    }

    @Override
    public ServiceInstance createServiceInstance(ServiceDefinition serviceDefinition,
                                                 String serviceInstanceId, String planId, String orgGuid, String spaceGuid)
            throws ServiceInstanceExistsException, ServiceBrokerException {
        logger.info("createServiceInstance: service instance id: " + serviceInstanceId);

        service.createHWInstance(serviceInstanceId);

        logger.info("createServiceInstance: hw Instance Id: " + serviceInstanceId);

        return new ServiceInstance(serviceInstanceId, serviceDefinition.getId(), planId, orgGuid, spaceGuid);
    }

    @Override
    public void deleteServiceInstance(String serviceInstanceId)
            throws ServiceBrokerException, ServiceInstanceDoesNotExistException, ServiceInstanceIsBoundException {
        service.destroyHWInstance(serviceInstanceId);
    }

    @Override
    public boolean serviceInstanceExists(String serviceInstanceId) throws ServiceBrokerException {
        return service.getHWInstanceId(serviceInstanceId) != null;
    }
}
