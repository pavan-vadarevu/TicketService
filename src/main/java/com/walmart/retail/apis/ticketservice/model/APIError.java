package com.walmart.retail.apis.ticketservice.model;

/**
 * 
 * @author Pavan K Vadarevu
 * 
 * APIError, POJO class defining custom error structure.
 *
 */

public class APIError {
	
	private String errorId;
	private String errorText;
	private String errorDetail;
	
	public APIError (String errorId, String errorText, String errorDetail) {
		this.errorId = errorId;
		this.errorText = errorText;
		this.errorDetail = errorDetail;
	}
	
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getErrorDetail() {
		return errorDetail;
	}
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

}
