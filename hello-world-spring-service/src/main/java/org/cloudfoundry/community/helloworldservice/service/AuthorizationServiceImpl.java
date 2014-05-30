package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.Authorization;
import org.cloudfoundry.community.helloworldservice.repo.Repo;
import org.cloudfoundry.community.helloworldservice.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    Repo repo;

    @Override
    public List<Authorization> getAllAuthorizations() {
        return repo.getAuthorizations();
    }

    @Override
    public Authorization getAuthorization(String authorizationId) {
        return repo.getAuthorization(authorizationId);
    }

    @Override
    public Authorization createAuthorization(String id,String accountId, String hwInstanceId) {
        if(repo.accountExists(accountId) &&
           repo.hwInstanceExists(hwInstanceId)) {
            Authorization authorization = new Authorization(id, accountId, hwInstanceId);
            repo.addAuthorization(authorization);
            return authorization;
        }
        else {
            return null;
        }
    }

    @Override
    public void destroyAuthorization(String id) {
        repo.deleteAuthorization(id);
    }

    @Override
    public boolean authorizationExists(String id) {
        return repo.authorizationExists(id);
    }

    @Override
    public boolean authorizationExists(String accountId, String hwInstanceId) {
        return repo.authorizationExists(accountId, hwInstanceId);
    }
}
