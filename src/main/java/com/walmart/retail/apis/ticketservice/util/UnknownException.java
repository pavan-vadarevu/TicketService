package com.walmart.retail.apis.ticketservice.util;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * BadRequestException, this exception will be thrown if the value for an input parameter is invalid.
 *
 */
public class UnknownException extends RuntimeException {

	public UnknownException() {
		super();
	}
	
	public UnknownException(String message) {
		super(message);
	}
}
