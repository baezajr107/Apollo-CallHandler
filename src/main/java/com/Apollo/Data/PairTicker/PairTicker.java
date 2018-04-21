package com.Apollo.Data.PairTicker;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

//this class contains the data for the value of a pair at a specific time

@JsonAutoDetect
public class PairTicker {

	@JsonProperty(value = "Bid")
	private double bid;
	@JsonProperty(value = "Ask")
	private double ask;
	@JsonProperty(value = "Last")
	private double last;
	
	private String pairName;
	
	public PairTicker() {
		
	}
	
	public PairTicker(String name) {
		pairName = name;
	}

	public double getBid() {
		return bid;
	}

	public void setBid(double bid) {
		this.bid = bid;
	}

	public double getAsk() {
		return ask;
	}

	public void setAsk(double ask) {
		this.ask = ask;
	}

	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}

	public String getPairName() {
		return pairName;
	}

	public void setPairName(String pairName) {
		this.pairName = pairName;
	}

	
	
	
	
}
