package com.walmart.retail.apis.ticketservice.util;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * BadRequestException, this exception will be thrown if the value for an input parameter is invalid.
 *
 */
public class BadRequestException extends RuntimeException {

	public BadRequestException() {
		super();
	}
	
	public BadRequestException(String message) {
		super(message);
	}
}
