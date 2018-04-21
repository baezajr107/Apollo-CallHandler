package com.Apollo.Data.PairBalance;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class PairBalance {
	@JsonProperty(value = "Currency")
	private String currency;
	@JsonProperty(value = "Balance")
	private double balance;
	@JsonProperty(value = "Available")
	private double available;
	@JsonProperty(value = "Pending")
	private double pending;
	@JsonProperty(value = "CryptoAddress")
	private String cryptoAddress;
	@JsonProperty(value = "Requested")
	private boolean requested;
	@JsonProperty(value = "Uiid")
	private String uiid;
	
	public PairBalance() {
		
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
	}

	public double getPending() {
		return pending;
	}

	public void setPending(double pending) {
		this.pending = pending;
	}

	public String getCryptoAddress() {
		return cryptoAddress;
	}

	public void setCryptoAddress(String cryptoAddress) {
		this.cryptoAddress = cryptoAddress;
	}

	public boolean isRequested() {
		return requested;
	}

	public void setRequested(boolean requested) {
		this.requested = requested;
	}

	public String getUiid() {
		return uiid;
	}

	public void setUiid(String uiid) {
		this.uiid = uiid;
	}
	
}
