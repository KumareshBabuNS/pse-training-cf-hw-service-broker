package org.cloudfoundry.community.helloworldservice.exception;

public class AccountExistsException extends Exception {

    private static final long serialVersionUID = -914571358227517785L;

    private String accountId;

    public AccountExistsException(String accountId) {
        this.accountId = accountId;
    }

    public String getMessage() {
        return String.format("Account with id %s already exists", accountId);
    }
}
