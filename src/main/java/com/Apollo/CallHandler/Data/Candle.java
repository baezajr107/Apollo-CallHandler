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
	
	
	
	
}
