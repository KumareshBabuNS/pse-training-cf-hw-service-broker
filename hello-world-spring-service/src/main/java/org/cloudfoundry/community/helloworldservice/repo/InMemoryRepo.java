package org.cloudfoundry.community.helloworldservice.repo;

import org.cloudfoundry.community.helloworldservice.model.Account;
import org.cloudfoundry.community.helloworldservice.model.Authorization;
import org.cloudfoundry.community.helloworldservice.model.HWInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

@Service
public class InMemoryRepo implements Repo {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryRepo.class);

    private static Map<String, Account> accounts =
            Collections.synchronizedMap(new HashMap<String, Account>());
    private static Map<String, HWInstance> hwInstances =
            Collections.synchronizedMap(new HashMap<String, HWInstance>());
    private static Map<String, Authorization> authorizations =
            Collections.synchronizedMap(new HashMap<String, Authorization>());

    @Override
    public void addAccount(Account account) {
        logger.info("addAccount: " + account);
        accounts.put(account.getId(), account);
        logger.info("keyset is now: " + accounts.keySet());
    }

    @Override
    public void deleteAccount(String accountId) {
        logger.info("deleteAccount: " + accountId);
        accounts.remove(accountId);
    }

    @Override
    public Account getAccount(String accountId) {
        logger.info("getAccount: " + accountId + " from: " + accounts.keySet());
        return accounts.get(accountId);
    }

    @Override
    public List<Account> getAccounts() {
        return (List) new ArrayList<Account>(accounts.values());
    }

    @Override
    public boolean accountExists(String accountId) {
        return accounts.containsKey(accountId);
    }

    @Override
    public void addAuthorization(Authorization authorization) {
        authorizations.put(authorization.getId(), authorization);
    }

    @Override
    public void deleteAuthorization(String id) {
        authorizations.remove(id);
    }

    @Override
    public Authorization getAuthorization(String id) {
        return authorizations.get(id);
    }

    @Override
    public boolean authorizationExists(String id) {
        return authorizations.containsKey(id);
    }

    @Override
    public boolean authorizationExists(String accountId, String hwInstanceId) {
        boolean exists = false;
        Authorization a = new Authorization("_foo_", accountId, hwInstanceId);
        for(Authorization authorization : authorizations.values()) {
            if(a.equals(authorization)) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    @Override
    public List<Authorization> getAuthorizations() {
        return (List) new ArrayList<Authorization>(authorizations.values());
    }


    @Override
    public void addHWInstance(HWInstance hwInstance) {
        hwInstances.put(hwInstance.getId(), hwInstance);
    }

    @Override
    public void deleteHWInstance(String hwInstanceId) {
        hwInstances.remove(hwInstanceId);
    }

    @Override
    public HWInstance getHWInstance(String hwInstanceId) {
        return hwInstances.get(hwInstanceId);
    }

    @Override
    public List<HWInstance> getHWInstances() {
        return (List) new ArrayList<HWInstance>(hwInstances.values());
    }

    @Override
    public boolean hwInstanceExists(String hwInstanceId) {
        return hwInstances.containsKey(hwInstanceId);
    }
}
