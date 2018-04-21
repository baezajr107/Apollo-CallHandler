package com.Apollo.Data.Config;

//this class contains all config settings for each pair the bot trades

public class PairConfig {
	private String pair;
	int maxDataPoints;
	double maxTradingValue;
	//all the variables for how you want to trade each pair.
	
	public PairConfig() {
		
	}
	public String getPair() {
		return pair;
	}
	public void setPair(String pair) {
		this.pair = pair;
	}
	public int getMaxDataPoints() {
		return maxDataPoints;
	}
	public void setMaxDataPoints(int maxDataPoints) {
		this.maxDataPoints = maxDataPoints;
	}
	public double getMaxTradingValue() {
		return maxTradingValue;
	}
	public void setMaxTradingValue(double maxTradingValue) {
		this.maxTradingValue = maxTradingValue;
	}
	
	
	
}
