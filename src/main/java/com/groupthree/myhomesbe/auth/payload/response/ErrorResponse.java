package com.groupthree.myhomesbe.auth.payload.response;

public class ErrorResponse {
	private String error;

	public ErrorResponse(String message) {
	    this.error = message;
	  }

	public String getMessage() {
		return error;
	}

	public void setMessage(String message) {
		this.error = message;
	}
}
