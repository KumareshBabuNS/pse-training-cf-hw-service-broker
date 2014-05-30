package org.cloudfoundry.community.helloworldservice.controller;

import javax.servlet.http.HttpServletResponse;

import org.cloudfoundry.community.helloworldservice.exception.HWServiceException;
import org.cloudfoundry.community.helloworldservice.model.ErrorDescriptionMessage;
import org.cloudfoundry.community.helloworldservice.model.ErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Base controller.
 * 
 * @author sgreenberg@gopivotal.com
 *
 */
public class BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
		
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleException(
			HttpMessageNotReadableException ex, 
			HttpServletResponse response) {
	    return getErrorResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleException(
			MethodArgumentNotValidException ex, 
			HttpServletResponse response) {
	    BindingResult result = ex.getBindingResult();
	    String message = "Missing required fields:";
	    for (FieldError error: result.getFieldErrors()) {
	    	message += " " + error.getField();
	    }
		return getErrorResponse(message, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<ErrorMessage> handleException(
			Exception ex, 
			HttpServletResponse response) {
		logger.warn("Exception", ex);
	    return getErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

    public ResponseEntity<ErrorMessage> getErrorResponse(String message, HttpStatus status) {
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(message), 
				status);
	}

    /**
     * an error message labeled with 'description' "field identifier"
     *
     * @param message
     * @param status
     * @return
     */
    public ResponseEntity<ErrorDescriptionMessage> getErrorDescriptionResponse(String message, HttpStatus status) {
        return new ResponseEntity<ErrorDescriptionMessage>(new ErrorDescriptionMessage(message),
                status);
    }

    /**
     * a standardized approach to dealing with broker errors
     * note that the use 'BrokerErrorResponse' results in the wrapping of the actual fault message
     * into a JSON constructs that label that message with 'description'
     *
     * (downside: the thrower of this exception has to have HttpStatus awareness...  :( )
     *
     * @param ex
     * @param response
     * @return
     */
    @ExceptionHandler(HWServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorDescriptionMessage> handleException(
            HWServiceException ex,
            HttpServletResponse response) {
        logger.warn("Service Broker Exception", ex);

        if(ex.getStatusCode() != null) {
            return new ResponseEntity<ErrorDescriptionMessage>(new ErrorDescriptionMessage(ex.getMessage()), ex.getStatusCode());
        }
        else {
            return new ResponseEntity<ErrorDescriptionMessage>(new ErrorDescriptionMessage(ex.getMessage()), HttpStatus.CONFLICT);
        }
    }
}
