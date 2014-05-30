package com.pivotal.cf.broker.service;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceDoesNotExistException;
import com.pivotal.cf.broker.exception.ServiceInstanceExistsException;
import com.pivotal.cf.broker.exception.ServiceInstanceIsBoundException;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.model.ServiceInstance;

public interface ServiceInstanceService {

    /**
	 * Create a new instance of a service
	 * @param service The service definition of the instance to create
	 * @param serviceInstanceId The id of the instance provided by the CloudController
	 * @param planId The id of the plan for this instance
	 * @param organizationGuid The guid of the org this instance belongs to
	 * @param spaceGuid The guid of the space this instance belongs to
	 * @return The newly created ServiceInstance
	 * @throws ServiceInstanceExistsException if the service instance already exists.
	 * @throws ServiceBrokerException if something goes wrong internally
	 */
	ServiceInstance createServiceInstance(ServiceDefinition service, String serviceInstanceId, String planId,
			String organizationGuid, String spaceGuid) 
			throws ServiceInstanceExistsException, ServiceBrokerException;
	
    /**
     * Delete the instance if it exists.
     * @param serviceInstanceId
     * @throws ServiceBrokerException
     * @throws ServiceInstanceDoesNotExistException
     * @throws ServiceInstanceIsBoundException
     */
	void deleteServiceInstance(String serviceInstanceId)
            throws ServiceBrokerException, ServiceInstanceDoesNotExistException, ServiceInstanceIsBoundException;

    /**
     * check if an instance exists
     * @param serviceInstanceId
     * @return
     * @throws ServiceBrokerException
     */
    boolean serviceInstanceExists(String serviceInstanceId)
            throws ServiceBrokerException;

}
