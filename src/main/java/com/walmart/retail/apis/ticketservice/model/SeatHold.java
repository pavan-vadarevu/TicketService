package com.walmart.retail.apis.ticketservice.model;

import java.util.List;

public class SeatHold {
	
	private List<SeatHoldStatus> seatHoldStatus;
	
	public List<SeatHoldStatus> getSeatHoldStatus() {
		return seatHoldStatus;
	}

	public void setSeatHoldStatus(List<SeatHoldStatus> seatHoldStatus) {
		this.seatHoldStatus = seatHoldStatus;
	}

}