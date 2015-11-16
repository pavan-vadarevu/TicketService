package com.walmart.retail.apis.ticketservice.impl;

import java.util.Optional;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;

import com.walmart.retail.apis.ticketservice.model.SeatHold;
import com.walmart.retail.apis.ticketservice.model.SeatHoldStatus;
import com.walmart.retail.apis.ticketservice.model.HoldSeats;
import com.walmart.retail.apis.ticketservice.model.ReserveSeats;
import com.walmart.retail.apis.ticketservice.model.Status;
import com.walmart.retail.apis.ticketservice.TicketService;
import com.walmart.retail.apis.ticketservice.dao.TicketServiceStatusRepository;
import com.walmart.retail.apis.ticketservice.util.BadRequestException;
import com.walmart.retail.apis.ticketservice.util.Constants;
import com.walmart.retail.apis.ticketservice.util.MissingMandatoryInputParametersException;
import com.walmart.retail.apis.ticketservice.util.SeatsNoLongerHeldException;
import com.walmart.retail.apis.ticketservice.util.UnknownException;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * TicketServiceImpl, implementation class for TicketService
 * 
 */

@Component
public class TicketServiceImpl implements TicketService {
	
	@Autowired TicketServiceStatusRepository ticketServiceStatusRepository;
	
	@Autowired HeldSeatsExpirationService heldSeatsExpirationService;

	/**
	 * This method returns the total number of available seats or the number of seats available at each level, based on the input received
	 * @param venueLevel a numeric venue level identifier to limit the search
	 * @return the number of tickets available on the provided level
	 * @see com.walmart.retail.apis.ticketservice.TicketService#numSeatsAvailable(java.util.Optional)
	 */
		
	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {

		if (venueLevel.isPresent()) {
			validateInput(venueLevel.get());
			return ticketServiceStatusRepository.findAvailableSeatCountByLevel(venueLevel.get());
		} else {
			return ticketServiceStatusRepository.findAllAvailableSeatCount();
		}
		
	}
	
	/**
	 * This method validates venue level value received in the input, venue level should be >=1 and <=4
	 * @param venueLevel a numeric venue level identifier to limit the search
	 * @throws BadRequestException
	 */
	public void validateInput (Integer input) {
		
		if ( !(input >=1 && input <= 4) ) {
			throw new BadRequestException("VenueLevel should be between 1 and 4");
		}
	}

	/**
	* Find and hold the best available seats for a customer
	*
	* @param numSeats the number of seats to find and hold
	* @param minLevel the minimum venue level
	* @param maxLevel the maximum venue level
	* @param customerEmail unique identifier for the customer
	* @return a SeatHold object identifying the specific seats and related
	information
	*/
	
	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {

		if (minLevel.isPresent()) {
			validateInput(minLevel.get());
		} else {
			minLevel = Optional.of(new Integer(0));
		}
		
		if (maxLevel.isPresent()) {
			validateInput(maxLevel.get());
		} else {
			minLevel = Optional.of(new Integer(4));
		}
		
		if ( numSeats <=0 || StringUtils.isBlank(customerEmail) ) {
			throw new MissingMandatoryInputParametersException (Constants.MISSING_MANDATORY_FIELDS_HOLD_SEATS_REQ);
		}
				
			List<Status> availableSeats = ticketServiceStatusRepository.findAvailableSeatsByLevel(minLevel.get());
			List<SeatHoldStatus> seatHoldStatusList = new ArrayList<SeatHoldStatus>();
			Random ran = new Random();
			int seatholdid = 100000+ran.nextInt(900000);
			int availableSeatsCount = availableSeats.size();
			int count = minLevel.get();
			int holdCount = 0;
			
			while (numSeats > availableSeatsCount) {
				count++;
				if (count > 4 ) {
					break;
				}
				availableSeats.addAll(ticketServiceStatusRepository.findAvailableSeatsByLevel(count));
				availableSeatsCount = availableSeats.size();
			}
			
			if (count > 4) {
				throw new BadRequestException("Requested seats can not be more than the available seats");
			}
			
			for (Status s: availableSeats) {
				if (holdCount == numSeats)
					break;
				java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
				holdCount = holdCount+ticketServiceStatusRepository.holdSeats(Constants.HELD, seatholdid, customerEmail, currentTimestamp, s.getSeatnumber(), s.getRowid(), s.getLevel());
				SeatHoldStatus seatHoldStatus = new SeatHoldStatus();
				seatHoldStatus.setSeatHoldId(seatholdid);
				seatHoldStatus.setSeatNumber(s.getRowid()+""+s.getSeatnumber());
				seatHoldStatus.setCustomerEmail(customerEmail);
				seatHoldStatus.setSeatLevel(s.getLevel());
				seatHoldStatusList.add(seatHoldStatus);
			}
			
			SeatHold seatHold = new SeatHold();
			seatHold.setSeatHoldStatus(seatHoldStatusList);
			
			// Start Async task of expiring seats that are held more than 60 sec
			try {
			      heldSeatsExpirationService.cancelSeatsHeld(Constants.EXPIRATION_TIME_IN_MILLIS, seatholdid, customerEmail);
			} catch (InterruptedException ex) {
				System.out.println("Interrupted Exception was Caught "+ex.getMessage());
				throw new UnknownException("Interrupted Exception was Caught "+ex.getMessage());
			}
			// Aysnc task end
						
			return seatHold;
		}


	/**
	* Commit seats held for a specific customer
	* @param ReserveSeats object containing seatHoldId the seat hold identifier and 
	* customerEmail the email address of the customer to which the seat hold is assigned
	* @return a reservation confirmation code
	*/
	@Override
	public String reserveSeats(ReserveSeats reserveseats) {

		if (reserveseats.getSeatHoldId() <=0 || StringUtils.isBlank(reserveseats.getCustomerEmail())) {
			throw new MissingMandatoryInputParametersException (Constants.MISSING_MANDATORY_FIELDS_RESERVE_SEATS_REQ);
		}
		
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		String confirmationid = Constants.CONF+reserveseats.getSeatHoldId();
        int updateCount = ticketServiceStatusRepository.reserveSeats(currentTimestamp, confirmationid, reserveseats.getSeatHoldId(), reserveseats.getCustomerEmail());
        if (updateCount > 0) {
        	return confirmationid;
        } else {
        	throw new SeatsNoLongerHeldException("Seats are not held or could have been expired, please try again");
        }
        
	}

}
