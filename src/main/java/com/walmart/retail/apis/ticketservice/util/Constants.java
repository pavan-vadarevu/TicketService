package com.walmart.retail.apis.ticketservice.util;

import java.io.Serializable;

public class Constants implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Constants () {}
	
	public static final String GET_ALL_AVAILABLE_SEATS_COUNT_BY_LEVEL_SQL = "select count(*) from Status s where s.level= :level and s.availability='AVAIL'";
	
	public static final String GET_ALL_AVAILABLE_SEATS_BY_LEVEL_SQL = "select s from Status s where s.level= :level and s.availability='AVAIL'";
	
	public static final String GET_ALL_AVAILABLE_SEATS_COUNT_SQL = "select count(*) from Status s where s.availability='AVAIL'";
	
	public static final String HOLD_SEATS_SQL = "update Status s set s.availability = ?1, s.seatholdid = ?2, s.email = ?3, s.lastactivityts = ?4 where s.seatnumber = ?5 and s.rowid = ?6 and s.level = ?7 and s.availability='AVAIL' ";
	
	public static final String RESERVE_SEATS_SQL = "update Status s set s.availability = 'CONF', s.lastactivityts = ?1, s.confirmationid = ?2 where s.seatholdid = ?3 and s.email = ?4 ";
	
	public static final String EXPIRE_HELD_SEATS_SQL = "update Status s set s.availability = 'AVAIL', s.seatholdid = 0 , s.email = NULL , s.lastactivityts = ?1 where s.availability = 'HELD' and s.seatholdid = ?2 and s.email = ?3";
	
	public static final long EXPIRATION_TIME_IN_MILLIS = 60000;
	
	public static final String MISSING_MANDATORY_FIELDS_HOLD_SEATS_REQ = "Missing Mandatory Input Parameter: numSeats and customerEmail are mandatory for holding seats";
	
	public static final String MISSING_MANDATORY_FIELDS_RESERVE_SEATS_REQ = "Missing Mandatory Input Parameter: seatHoldId and customerEmail are mandatory for reserving seats";
	
	public static final String CONF = "CONF";

	public static final String HELD = "HELD";
	
}
