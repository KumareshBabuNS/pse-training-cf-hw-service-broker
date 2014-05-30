package org.cloudfoundry.community.helloworldservice.exception;

import org.springframework.http.HttpStatus;

public class HWServiceException extends Exception {
    private static final long serialVersionUID = -5544859893499349135L;
    private String message;
    private HttpStatus statusCode;

    public HWServiceException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    public HWServiceException(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
