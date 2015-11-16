package com.walmart.retail.apis.ticketservice.web;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.walmart.retail.apis.ticketservice.model.HoldSeats;
import com.walmart.retail.apis.ticketservice.model.ReserveSeats;
import com.walmart.retail.apis.ticketservice.model.SeatHold;
import com.walmart.retail.apis.ticketservice.TicketService;
import com.walmart.retail.apis.ticketservice.dao.TicketServiceStatusRepository;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * TicketServiceAPI, this class exposes numSeatsAvailable, findAndHoldSeats and reserveSeats methods 
 * as Restful APIs with unique URI mapping for each method
 *
 */

@Component
@RestController
@RequestMapping("/retail/apis/ticketservice")
public class TicketServiceAPI {
	
	@Autowired TicketServiceStatusRepository ticketServiceStatusRepository;
	
	@Autowired TicketService ticketService;
	
	/**
	 * This method is the landing place for GET /retail/apis/ticketservice API call
	 * @param venuelevel
	 * @return ResponseEntity with Http Status code and the number of available seats
	 */
    @RequestMapping(value="",method=RequestMethod.GET)
	public ResponseEntity<Integer> numSeatsAvailable( @RequestParam(value = "venuelevel", required = false) Integer venuelevel) {
		
    	Optional<Integer> venuelevel_opt = Optional.ofNullable(venuelevel); 
    	return new ResponseEntity<Integer>(ticketService.numSeatsAvailable(venuelevel_opt), HttpStatus.OK);
  
	}
	
    /**
     * This method is the landing place for POST /retail/apis/ticketservice/holdseats API call
     * @param holdseats
     * @return ResponseEntity with HttpStatus and SeatHold object containing details such as seatholdid, email address, seat number and level
     */
    @RequestMapping(value="/holdseats",method=RequestMethod.POST)
	public ResponseEntity<SeatHold> findAndHoldSeats(@RequestBody HoldSeats holdseats) {
		System.out.println(holdseats.toString());
    	Optional<Integer> minlevel_opt = Optional.ofNullable(holdseats.getMinLevel()); 
    	Optional<Integer> maxlevel_opt = Optional.ofNullable(holdseats.getMaxLevel()); 

		return new ResponseEntity<SeatHold>(ticketService.findAndHoldSeats(holdseats.getNumSeats(),minlevel_opt,maxlevel_opt,holdseats.getCustomerEmail()),HttpStatus.OK);
	}
	
    /**
     * This method is the landing place for POST /retail/apis/ticketservice/reserveseats API call
     * @param reserveseats
     * @return ResponseEntity with HttpStatus and seat confirmation ID
     */
    @RequestMapping(value="/reserveseats",method=RequestMethod.POST)
		public ResponseEntity<String> reserveSeats(@RequestBody ReserveSeats reserveseats) {
    	return new ResponseEntity<String>(ticketService.reserveSeats(reserveseats),HttpStatus.OK);
	}
	
}