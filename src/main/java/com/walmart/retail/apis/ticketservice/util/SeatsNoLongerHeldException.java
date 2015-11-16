package com.walmart.retail.apis.ticketservice.util;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * SeatsNoLongerHeldException, this exception will be thrown when someone tries to reserve seats which are not held
 * 
 */

public class SeatsNoLongerHeldException extends RuntimeException {

	public SeatsNoLongerHeldException() {
		super();
	}
	
	public SeatsNoLongerHeldException(String message) {
		super(message);
	}
}
