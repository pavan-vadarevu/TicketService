package com.walmart.retail.apis.ticketservice.model;

import java.io.Serializable;

public class ReserveSeats implements Serializable {

	private static final long serialVersionUID = 2799874095817497911L;
	private int seatHoldId;
	private String customerEmail;
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

}
