package com.pivotal.cf.broker.exception;

public class ServiceInstanceIsBoundException extends Exception {
    private static final long serialVersionUID = -1L;

    private String serviceInstanceId;

    public ServiceInstanceIsBoundException(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    public String getMessage() {
        return "ServiceInstance is currently bound: serviceInstance.id = "
                + serviceInstanceId;
    }
}
