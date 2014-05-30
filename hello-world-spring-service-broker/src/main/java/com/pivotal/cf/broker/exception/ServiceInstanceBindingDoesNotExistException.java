package com.pivotal.cf.broker.exception;

public class ServiceInstanceBindingDoesNotExistException extends Exception {

	private static final long serialVersionUID = -1L;

	private String bindingId;

	public ServiceInstanceBindingDoesNotExistException(String bindingId) {
		this.bindingId = bindingId;
	}
	
	public String getMessage() {
        return "ServiceInstanceBinding does not exist: id = " + bindingId;
	}
}
