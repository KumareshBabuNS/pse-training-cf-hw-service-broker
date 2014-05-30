package org.cloudfoundry.community.helloworldservice.controller;

import org.cloudfoundry.community.helloworldservice.exception.AccountExistsException;
import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.cloudfoundry.community.helloworldservice.model.Account;
import org.cloudfoundry.community.helloworldservice.service.AccountService;
import org.cloudfoundry.community.helloworldservice.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AccountController extends BaseController {
    public static final String BASE_PATH = "/accounts";

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
    public @ResponseBody
    List<Account> getAccounts() {
        logger.debug("GET: " + BASE_PATH);
        return service.getAllAccounts();
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> createAccount(
            @PathVariable("id") String accountId,
            @RequestParam("password") String password) throws
            AccountExistsException,
            HWServiceException {
        logger.info("PUT: " + BASE_PATH + "/{id}: " + accountId);

        service.createAccount(new Account(accountId, Utils.stringToSecureHash(password)));

        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAccount(
            @PathVariable("id") String accountId) {
        logger.debug( "DELETE: " + BASE_PATH + "/{id} :" + accountId);
        if(!service.accountExists(accountId)) {
            return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
        }

        service.destroyAccount(accountId);

        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }
}
