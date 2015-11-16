package com.walmart.retail.apis.ticketservice.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.walmart.retail.apis.ticketservice.model.APIError;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * This class catches all the API exceptions and maps them to appropriate HTTP Status codes and custom error messages.
 *
 */

@ControllerAdvice
public class ExceptionHandlerService {

    @ExceptionHandler({ BadRequestException.class })
    public ResponseEntity<APIError> handleBadRequest (BadRequestException e) {
    	APIError apiError = new APIError("200001","REQUEST_VALIDATION_ERROR",e.getMessage());
    	return new ResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ MissingMandatoryInputParametersException.class })
    public ResponseEntity<APIError> handleBadRequest (MissingMandatoryInputParametersException e) {
    	APIError apiError = new APIError("200001","REQUEST_VALIDATION_ERROR",e.getMessage());
    	return new ResponseEntity(apiError,HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({ SeatsNoLongerHeldException.class })
    public ResponseEntity<APIError> handleBadRequest (SeatsNoLongerHeldException e) {
    	APIError apiError = new APIError("200011","RESERVATION_FULFILLMENT_ERROR",e.getMessage());
    	return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    @ExceptionHandler({ UnknownException.class })
    public ResponseEntity<APIError> handleBadRequest (UnknownException e) {
    	APIError apiError = new APIError("200099","UNEXPECTED_ERROR",e.getMessage());
    	return new ResponseEntity(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
