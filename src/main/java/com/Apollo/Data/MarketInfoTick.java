package com.Apollo.Data;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.github.ccob.bittrex4j.dao.Tick;
import com.fasterxml.jackson.annotation.JsonProperty;

//this class contains the data for the value of a pair at a specific time

public class MarketInfoTick extends Tick{
	

	
	public MarketInfoTick(ZonedDateTime startTime, double open, double high, double low, double close, double volume, double baseVolume) {
		super(startTime, open, high, low, close, volume, baseVolume);
	}
	

	
	
	
	
}
