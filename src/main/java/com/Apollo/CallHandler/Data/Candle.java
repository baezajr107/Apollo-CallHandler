package com.Apollo.CallHandler.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.ccob.bittrex4j.dao.Tick;
import com.fasterxml.jackson.annotation.JsonProperty;

//this class contains the data for the value of a pair at a specific time

public class Candle{
	
	private ZonedDateTime startTime;
	private double open;
	private double close;
	private double low;
	private double high;
	private double volume;
	
	//TA indicators
	private double sma;
	private double bbLow;
	private double bbHigh;
	private double rsi;
	
	
	public Candle(Tick tick) {
		startTime = tick.getStartTime().withZoneSameInstant(ZoneId.systemDefault());
		open = tick.getOpen();
		close = tick.getClose();
		low = tick.getLow();
		high = tick.getHigh();
		volume = tick.getBaseVolume();
	}
	
	public Candle(double initValue) {
		startTime = ZonedDateTime.now();
		open = initValue;
		close = initValue;
		low = initValue;
		high = initValue;
		volume = 0;
	}
	
	public void updateValues(double min, double max, double closeValue, double volume) {
		this.volume+=volume;
		if(max>high) {
			high = closeValue;
		}
		if(min<low) {
			low = closeValue;
		}
		close = closeValue;
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getSma() {
		return sma;
	}

	public void setSma(double sma) {
		this.sma = sma;
	}

	public double getBbLow() {
		return bbLow;
	}

	public void setBbLow(double bbLow) {
		this.bbLow = bbLow;
	}

	public double getBbHigh() {
		return bbHigh;
	}

	public void setBbHigh(double bbHigh) {
		this.bbHigh = bbHigh;
	}

	public double getRsi() {
		return rsi;
	}

	public void setRsi(double rsi) {
		this.rsi = rsi;
	}
	
	
	
	
	
	
}
