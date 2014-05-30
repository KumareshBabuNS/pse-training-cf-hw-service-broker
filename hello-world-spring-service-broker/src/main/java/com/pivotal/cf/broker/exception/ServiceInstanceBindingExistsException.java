package com.pivotal.cf.broker.exception;

public class ServiceInstanceBindingExistsException extends Exception {

	private static final long serialVersionUID = -914571358227517785L;
	
	private String bindingId;
	
	public ServiceInstanceBindingExistsException(String bindingId) {
		this.bindingId = bindingId;
	}
	
	public String getMessage() {
		return "ServiceInstanceBinding already exists with id: "
				+ bindingId;
	}
}
