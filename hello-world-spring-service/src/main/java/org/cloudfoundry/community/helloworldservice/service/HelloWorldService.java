package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.springframework.security.access.AccessDeniedException;

public interface HelloWorldService {
    public String getMessage(String userId, String hwInstanceId) throws
            AccessDeniedException, HWServiceException;
}
