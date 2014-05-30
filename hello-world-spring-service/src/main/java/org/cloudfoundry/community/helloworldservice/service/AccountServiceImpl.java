package org.cloudfoundry.community.helloworldservice.service;

import org.cloudfoundry.community.helloworldservice.model.Account;
import org.cloudfoundry.community.helloworldservice.repo.Repo;
import org.cloudfoundry.community.helloworldservice.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private static String ADMIN_ACCOUNT_ID = "admin";
    private static String ADMIN_PASSWORD = "admin";

    @Autowired
    Repo repo;

    @Override
    public List<Account> getAllAccounts() {

        return repo.getAccounts();
    }

    @Override
    public void createAccount(Account account) {
        logger.info("adding account: " + account);
        repo.addAccount(account);
    }

    @Override
    public void destroyAccount(String accountId) {
        repo.deleteAccount(accountId);
    }

    @Override
    public boolean accountExists(String accountId) {

        return repo.accountExists(accountId);
    }

    @Override
    public boolean authenticate(String id, String pwd) {
        logger.info("Authenticating: " + id + " / " + pwd);
        boolean success = false;

        if(id.equals(ADMIN_ACCOUNT_ID)) {
            success = pwd.equals(ADMIN_PASSWORD);
        }
        else {
            Account acc = repo.getAccount(id);

            logger.info("FOUND account: " + acc);
            if (acc != null) {
                String hash = Utils.stringToSecureHash(pwd);
                logger.info("HASH of entered pwd: " + hash);
                success = acc.getPwdHash().equals(hash);
            }
            else {
                logger.info("EXISTING ACCS: " + repo.getAccounts());
            }
        }
        return success;
    }
}
