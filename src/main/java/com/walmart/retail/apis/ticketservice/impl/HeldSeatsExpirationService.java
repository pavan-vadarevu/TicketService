package com.walmart.retail.apis.ticketservice.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.walmart.retail.apis.ticketservice.dao.TicketServiceStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class HeldSeatsExpirationService {
	
	@Autowired TicketServiceStatusRepository ticketServiceStatusRepository;
	
	@Async
	public Future<Integer> cancelSeatsHeld(long delay, int seatheldid , String customeremail) throws InterruptedException {
		System.out.println("Async task to cancel seats which are held more than 60 seconds - started");
		Thread.sleep(delay);
		Timestamp lastactivityts = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Integer seatsNoLongerHeld = ticketServiceStatusRepository.expireHeldSeats(lastactivityts, seatheldid , customeremail);
		System.out.println("Async task to cancel seats which are held more than 60 seconds - ended");
		System.out.println("Number of seats expired/cancelled = "+seatsNoLongerHeld);
		return new AsyncResult<Integer>(seatsNoLongerHeld);
	}

}
