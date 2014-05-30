package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.cloudfoundry.community.helloworldservice.repo.Repo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    @Autowired
    Repo repo;

    @Override
    public String getMessage(String userId, String hwInstanceId) throws
            AccessDeniedException, HWServiceException {
        boolean hwInstanceExists = repo.hwInstanceExists(hwInstanceId);
        boolean authorizationExists = repo.authorizationExists(userId, hwInstanceId);

        if(!hwInstanceExists) {
            throw new HWServiceException(
                    String.format("HWInstance %s does not exist", hwInstanceId), HttpStatus.NOT_FOUND);
        }
        if(!authorizationExists) {
            throw new AccessDeniedException(
                    String.format("HWInstance %s exists, but user %s does not have access",
                            hwInstanceId, userId));
        }

        return "Hello World [" + hwInstanceId + "]";
    }
}
