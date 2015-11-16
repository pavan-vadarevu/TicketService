package com.walmart.retail.apis.ticketservice.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(StatusPK.class)
public class Status implements Serializable {
	

	private static final long serialVersionUID = -5282029190110358490L;
	
	@Id
	private int seatnumber;
	@Id
	private int level;
	@Id
	private char rowid;
	
	private String availability;
	private int seatholdid;

	private String confirmationid;
	private String email;
	private Timestamp lastactivityts;
	
	Status () {}
	
	public int getSeatnumber() {
		return seatnumber;
	}

	public void setSeatnumber(int seatnumber) {
		this.seatnumber = seatnumber;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public char getRowid() {
		return rowid;
	}

	public void setRowid(char rowid) {
		this.rowid = rowid;
	}
	
	public String getConfirmationid() {
		return confirmationid;
	}

	public void setConfirmationid(String confirmationid) {
		this.confirmationid = confirmationid;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public int getSeatholdid() {
		return seatholdid;
	}

	public void setSeatholdid(int seatholdid) {
		this.seatholdid = seatholdid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Timestamp getLastactivityts() {
		return lastactivityts;
	}

	public void setLastactivityts(Timestamp lastactivityts) {
		this.lastactivityts = lastactivityts;
	}
	
	@Override
	public String toString() {
		return ("seatnumber = "+this.seatnumber+",level="+this.level+",availability="+this.availability+",confirmationid="+this.seatholdid+",email="+this.email+",lastactivityts="+this.lastactivityts);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((lastactivityts == null) ? 0 : lastactivityts.hashCode());
		result = prime * result + level;
		result = prime * result + rowid;
		result = prime * result + seatholdid;
		result = prime * result + seatnumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (lastactivityts == null) {
			if (other.lastactivityts != null)
				return false;
		} else if (!lastactivityts.equals(other.lastactivityts))
			return false;
		if (level != other.level)
			return false;
		if (rowid != other.rowid)
			return false;
		if (seatholdid != other.seatholdid)
			return false;
		if (seatnumber != other.seatnumber)
			return false;
		return true;
	}
	
}

class StatusPK implements Serializable {

	private static final long serialVersionUID = 6593135354231717603L;
	private int seatnumber;
	private int level;
	private char rowid;
}