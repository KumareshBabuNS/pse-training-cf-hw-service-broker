package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.Authorization;

import java.util.List;

public interface AuthorizationService {


    public List<Authorization> getAllAuthorizations();

    public Authorization getAuthorization(String authorizationId);

    public Authorization createAuthorization(String id,String accountId, String hwInstanceId);

    public void destroyAuthorization(String id);

    public boolean authorizationExists(String id);

    public boolean authorizationExists(String accountId, String hwInstanceId);
}
