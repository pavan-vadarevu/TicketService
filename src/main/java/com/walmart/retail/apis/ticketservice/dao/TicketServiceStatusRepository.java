package com.walmart.retail.apis.ticketservice.dao;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.walmart.retail.apis.ticketservice.model.Status;
import com.walmart.retail.apis.ticketservice.util.Constants;


public interface TicketServiceStatusRepository extends JpaRepository<Status, Integer> {
	
	@Query(Constants.GET_ALL_AVAILABLE_SEATS_COUNT_BY_LEVEL_SQL)
	Integer findAvailableSeatCountByLevel(@Param(value="level") Integer level);
	
	@Query(Constants.GET_ALL_AVAILABLE_SEATS_BY_LEVEL_SQL)
	List<Status> findAvailableSeatsByLevel(@Param(value="level") Integer level);
	
	@Query(Constants.GET_ALL_AVAILABLE_SEATS_COUNT_SQL)
	Integer findAllAvailableSeatCount();
	
	@Transactional
	@Modifying
	@Query(Constants.HOLD_SEATS_SQL)
	int holdSeats(String availability,int seatholdid,String email,Timestamp lastactivityts,int seatnumber,char rowid,int level);
	
	@Transactional
	@Modifying
	@Query(Constants.RESERVE_SEATS_SQL)
	int reserveSeats(Timestamp lastactivityts,String confirmationid, int seatHoldId, String customerEmail);
	
	@Transactional
	@Modifying
	@Query(Constants.EXPIRE_HELD_SEATS_SQL)
	int expireHeldSeats(Timestamp lastactivityts,int seatholdid, String customeremail);
		
}
