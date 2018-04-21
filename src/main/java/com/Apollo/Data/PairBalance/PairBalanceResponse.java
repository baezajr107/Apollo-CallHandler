package com.Apollo.Data.PairBalance;

import com.Apollo.Data.PairTicker.PairTicker;

public class PairBalanceResponse {
	private String success;
	private String message;
	private PairBalance result;
	
	public PairBalanceResponse() {
		
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

	public PairBalance getResult() {
		return result;
	}

	public void setResult(PairBalance result) {
		this.result = result;
	}
	
	
}
