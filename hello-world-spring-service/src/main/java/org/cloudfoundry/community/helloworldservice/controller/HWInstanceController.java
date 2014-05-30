package org.cloudfoundry.community.helloworldservice.controller;

import org.cloudfoundry.community.helloworldservice.exception.HWInstanceExistsException;
import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.cloudfoundry.community.helloworldservice.exception.HWInstanceDoesNotExistException;

import org.cloudfoundry.community.helloworldservice.model.ErrorDescriptionMessage;
import org.cloudfoundry.community.helloworldservice.model.HWInstance;
import org.cloudfoundry.community.helloworldservice.service.HWInstanceService;

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
public class HWInstanceController extends BaseController {

    public static final String BASE_PATH = "/hwinstances";

    private static final Logger logger = LoggerFactory.getLogger(HWInstanceController.class);

    private HWInstanceService service;

    @Autowired
    public HWInstanceController(HWInstanceService service) {
        this.service = service;
    }

    @RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
    public @ResponseBody
    List<HWInstance> getServiceInstances() {
        logger.info("GET: " + BASE_PATH);
        return service.getAllHWInstances();
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    HWInstance getServiceInstance(
            @PathVariable("id") String hwInstanceId) throws
            HWServiceException, HWInstanceDoesNotExistException {
        logger.info("GET: " + BASE_PATH + "/{id}: " + hwInstanceId);
        return service.getHWInstance(hwInstanceId);
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> createHWInstance(
            @PathVariable("id") String hwInstanceId) throws
            HWInstanceExistsException,
            HWServiceException,
            HWInstanceExistsException {
        logger.info("PUT: " + BASE_PATH + "/{id}: " + hwInstanceId);

        if (service.hwInstanceExists(hwInstanceId)) {
            throw new HWInstanceExistsException(hwInstanceId);
        }
        service.createHWInstance(new HWInstance(hwInstanceId));

        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }

    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteServiceInstance(
            @PathVariable("id") String hwInstanceId) throws
            HWInstanceDoesNotExistException {
        logger.info( "DELETE: " + BASE_PATH + "/{id}: " + hwInstanceId);
        if (!service.hwInstanceExists(hwInstanceId)) {
            throw new HWInstanceDoesNotExistException(hwInstanceId);
        }

        service.destroyHWInstance(hwInstanceId);

        return new ResponseEntity<String>("{}", HttpStatus.OK);
    }



    @ExceptionHandler(HWInstanceExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptionMessage> handleException(
            HWInstanceExistsException ex,
            HttpServletResponse response) {
        return getErrorDescriptionResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HWInstanceDoesNotExistException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptionMessage> handleException(
            HWInstanceDoesNotExistException ex,
            HttpServletResponse response) {
        return getErrorDescriptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
