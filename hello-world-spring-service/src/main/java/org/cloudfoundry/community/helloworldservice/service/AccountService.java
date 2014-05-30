package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.Account;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    public void createAccount(Account account);
    public void destroyAccount(String accountId);

    public boolean accountExists(String accountId);

    public boolean authenticate(String id, String pwd);
}
