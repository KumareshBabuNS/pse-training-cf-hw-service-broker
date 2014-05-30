package org.cloudfoundry.community.helloworldservice.repo;

import org.cloudfoundry.community.helloworldservice.model.Account;
import org.cloudfoundry.community.helloworldservice.model.Authorization;
import org.cloudfoundry.community.helloworldservice.model.HWInstance;

import java.util.List;

public interface Repo {

    public void addAccount(Account account);
    public void deleteAccount(String id);
    public Account getAccount(String id);
    public List<Account> getAccounts();
    public boolean accountExists(String id);

    public void addAuthorization(Authorization authorization);
    public void deleteAuthorization(String id);
    public Authorization getAuthorization(String id);
    public List<Authorization> getAuthorizations();
    public boolean authorizationExists(String id);
    public boolean authorizationExists(String accountId, String hwInstanceId);

    public void addHWInstance(HWInstance hwInstance);
    public void deleteHWInstance(String id);
    public HWInstance getHWInstance(String id);
    public List<HWInstance> getHWInstances();
    public boolean hwInstanceExists(String id);
}
