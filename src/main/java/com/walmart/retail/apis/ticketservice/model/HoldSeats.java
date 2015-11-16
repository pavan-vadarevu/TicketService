package com.walmart.retail.apis.ticketservice.model;

import java.io.Serializable;


public class HoldSeats implements Serializable {
	
	private static final long serialVersionUID = -4029236535881021364L;
	
	private int numSeats;
	private Integer minLevel;
	private Integer maxLevel;
	private String customerEmail;
	
	public HoldSeats() {}
	
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public Integer getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(Integer minLevel) {
		this.minLevel = minLevel;
	}
	public Integer getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	@Override
	public String toString() {
		return ("HoldSeats="+this.numSeats+"::"+this.minLevel+"::"+this.maxLevel+"::"+this.customerEmail);
	}

}
