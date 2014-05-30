package org.cloudfoundry.community.helloworldservice.exception;

import org.cloudfoundry.community.helloworldservice.model.Authorization;

public class AuthorizationDoesNotExistException extends Exception {

    private static final long serialVersionUID = -914571338227517785L;

    private Authorization authorization;

    public AuthorizationDoesNotExistException(Authorization authorization) {
        this.authorization = authorization;
    }

    public String getMessage() {
        return String.format("Authorization does not exist: ", authorization);
    }
}
