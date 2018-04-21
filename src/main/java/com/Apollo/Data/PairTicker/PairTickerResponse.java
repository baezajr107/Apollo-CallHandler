package com.Apollo.Data.PairTicker;

public class PairTickerResponse {
	private String success;
	private String message;
	private PairTicker result;
	
	public PairTickerResponse() {
		
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PairTicker getResult() {
		return result;
	}

	public void setResult(PairTicker result) {
		this.result = result;
	}
	
	
}
