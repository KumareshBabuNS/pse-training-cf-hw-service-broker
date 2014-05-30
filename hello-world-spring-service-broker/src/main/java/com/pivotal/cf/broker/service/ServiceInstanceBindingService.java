package com.pivotal.cf.broker.service;

import com.pivotal.cf.broker.exception.ServiceBrokerException;
import com.pivotal.cf.broker.exception.ServiceInstanceBindingDoesNotExistException;
import com.pivotal.cf.broker.exception.ServiceInstanceBindingExistsException;
import com.pivotal.cf.broker.exception.ServiceInstanceDoesNotExistException;
import com.pivotal.cf.broker.model.ServiceInstanceBinding;

public interface ServiceInstanceBindingService {

	/**
	 * Create a new binding to a service instance.
	 * @param bindingId The id provided by the cloud controller
	 * @param serviceInstanceId The id of the service instance
	 * @param serviceId The id of the service
	 * @param planId The plan used for this binding
	 * @param appGuid The guid of the app for the binding
	 * @return
	 * @throws ServiceInstanceBindingExistsException if the same binding already exists.  
	 */
	ServiceInstanceBinding createServiceInstanceBinding(
			String bindingId, String serviceInstanceId, String serviceId, String planId, String appGuid)
			throws ServiceInstanceDoesNotExistException, ServiceInstanceBindingExistsException, ServiceBrokerException;

	/**
	 * Delete the service instance binding.  If a binding doesn't exist, 
	 * return null.
	 * @param bindingId
	 * @return The id of the deleted ServiceInstanceBinding or null if one does not exist.
	 */
	String deleteServiceInstanceBinding(String bindingId)
            throws ServiceBrokerException, ServiceInstanceBindingDoesNotExistException;


    /**
     * Check to see if there exists a service instance binding that is bound to
     * a service instance with the passed-in id
     * @param serviceInstanceId
     * @return
     */
    boolean isServiceInstanceBound(String serviceInstanceId);

    /**
     * check if an binding exists
     * @param bindingId
     * @return
     * @throws ServiceBrokerException
     */
    boolean serviceInstanceBindingExists(String bindingId)
            throws ServiceBrokerException;

}
