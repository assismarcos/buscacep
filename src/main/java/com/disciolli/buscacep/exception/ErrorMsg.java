package com.disciolli.buscacep.exception;

public class ErrorMsg {
	private String message;
	
	public ErrorMsg() {
	}
	
	public ErrorMsg(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
