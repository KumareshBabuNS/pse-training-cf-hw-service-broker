package com.pivotal.cf.broker.exception;

public class ServiceInstanceExistsException extends Exception {

	private static final long serialVersionUID = -914571358227517785L;
	
	private String serviceInstanceId;
	
	public ServiceInstanceExistsException(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}
	
	public String getMessage() {
		return "ServiceInstance with the given id already exists: ServiceInstance.id = " + serviceInstanceId;
	}
}
