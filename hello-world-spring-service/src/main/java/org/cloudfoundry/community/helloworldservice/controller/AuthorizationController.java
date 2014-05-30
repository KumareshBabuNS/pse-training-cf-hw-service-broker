package org.cloudfoundry.community.helloworldservice.controller;

import org.cloudfoundry.community.helloworldservice.exception.*;
import org.cloudfoundry.community.helloworldservice.model.Authorization;
import org.cloudfoundry.community.helloworldservice.model.ErrorDescriptionMessage;
import org.cloudfoundry.community.helloworldservice.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class AuthorizationController extends BaseController {
    public static final String BASE_PATH = "/authorizations";

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private AuthorizationService service;

    @Autowired
    public AuthorizationController(
            AuthorizationService service)
    {
        this.service = service;
    }

    @RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
    public @ResponseBody
    List<Authorization> getAuthorizations() {
        logger.debug("GET: " + BASE_PATH);
        return service.getAllAuthorizations();
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Authorization getAuthorization(
            @PathVariable("id") String authorizationId) throws
            HWServiceException, AuthorizationDoesNotExistException {
        logger.info("GET: " + BASE_PATH + "/{id}: " + authorizationId);
        return service.getAuthorization(authorizationId);
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.PUT)
    public @ResponseBody Authorization createAuthorization(
            @PathVariable("id") String id,
            @RequestParam("accountId") String accountId,
            @RequestParam("hwInstanceId") String hwInstanceId) throws
            AuthorizationExistsException,
            HWServiceException {
        logger.info("PUT: " + BASE_PATH + "/{id} params: " + accountId + " ; " + hwInstanceId);

        if(service.authorizationExists(accountId, hwInstanceId)) {
            throw new AuthorizationExistsException(accountId, hwInstanceId);
        }
        Authorization authorization =  service.createAuthorization(id, accountId, hwInstanceId);
        if(authorization == null) {
            throw new HWServiceException(String.format("Create authorization failure: account (%s) or HWInstance (%s) nonexistent",
                    accountId, hwInstanceId));
        }

        return authorization;
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAuthorization(
            @PathVariable("id") String id) {
        logger.debug( "DELETE: " + BASE_PATH + "/{id} params: " + id);

        if(!service.authorizationExists(id)) {
            return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
        }

        service.destroyAuthorization(id);

        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }

    @ExceptionHandler(AuthorizationExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptionMessage> handleException(
            AuthorizationExistsException ex,
            HttpServletResponse response) {
        return getErrorDescriptionResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
