package org.cloudfoundry.community.helloworldservice.controller;

import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.cloudfoundry.community.helloworldservice.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class HelloWorldController extends BaseController {
    public static final String BASE_PATH = "/helloworld";

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    private HelloWorldService service;

    @Autowired
    public HelloWorldController(HelloWorldService service) {
        this.service = service;
    }

    @RequestMapping(value = BASE_PATH + "/{hwInstanceId}", method = RequestMethod.GET)
    public ResponseEntity<String> echo(
            @PathVariable("hwInstanceId") String hwInstanceId, Principal principal) throws
            AccessDeniedException, HWServiceException
    {
        logger.debug("GET: " + BASE_PATH + "/" + hwInstanceId);

        return new ResponseEntity<String>(service.getMessage(principal.getName(), hwInstanceId), HttpStatus.OK);
    }
}
