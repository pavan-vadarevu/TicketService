package com.walmart.retail.apis.ticketservice.util;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * MissingMandatoryInputParametersException, this exception will be thrown if any mandatory input parameters are missing
 * 
 */

public class MissingMandatoryInputParametersException extends RuntimeException {

	public MissingMandatoryInputParametersException() {
		super();
	}
	
	public MissingMandatoryInputParametersException(String message) {
		super(message);
	}
}
