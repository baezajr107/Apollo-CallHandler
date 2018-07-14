package com.Apollo.CallHandler.Data;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.Apollo.CallHandler.Utils.TechnicalAnalysis;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.ccob.bittrex4j.dao.Fill;
import com.github.ccob.bittrex4j.dao.Tick;

@Entity
@Table(name="traded_coins")
public class TradedCoin {

	@Id
	@Column(name="coin_name")
	private String coinName;
	
	@Transient
	@JsonInclude
	private List<Candle> candles1h;
	
	@Transient
	@JsonInclude
	private List<Candle> candles30m;
	
	@Transient
	@JsonInclude
	private List<Candle> candles5m;
	
	@Transient
	@JsonInclude
	private List<Fill> fills;
	
	public TradedCoin() {
		fills = new ArrayList<Fill>();
	}
	
	public TradedCoin(String name) {
		coinName=name;
	}
	
	//the following 3 methods shift the candles back to create a new candle for a period
	public void shift5m() {
		System.out.println("Shifting 5 minute candles back for coin: " + coinName);

		ZonedDateTime nowMinus5 = ZonedDateTime.now().minusMinutes(5);
		setFinalValues(candles5m,nowMinus5);
		
		candles5m.add(new Candle(candles5m.get(candles5m.size()-1).getClose()));
		
	}
	
	public void shift30m() {
		System.out.println("Shifting 30 minute candles back for coin: " + coinName);
		
		ZonedDateTime nowMinus30 = ZonedDateTime.now().minusMinutes(30);
		setFinalValues(candles30m,nowMinus30);
		
		candles30m.add(new Candle(candles30m.get(candles30m.size()-1).getClose()));
	}
	
	public void shift1h() {
		System.out.println("Shifting 1 hour candles back for coin: " + coinName);
		
		ZonedDateTime nowMinus1h = ZonedDateTime.now().minusHours(1);
		setFinalValues(candles1h,nowMinus1h);
		
		candles1h.add(new Candle(candles1h.get(candles1h.size()-1).getClose()));
		fills.clear();

	}
	
	private void setFinalValues(List<Candle> candles,ZonedDateTime lowerBound) {
		//get the last candle and set the final values
		Candle lastCandle = candles.get(candles.size()-1);
		
		if(fills.size()>0) {
			List<Fill> fillsSinceTimeframeStart = fills.stream()
			.filter(fill -> fill.getTimeStamp().isAfter(lowerBound))
			.collect(Collectors.toList());
			
			double min = fillsSinceTimeframeStart.stream()
					.mapToDouble(Fill::getPrice)
					.min()
					.getAsDouble();
			double max = fillsSinceTimeframeStart.stream()
					.mapToDouble(Fill::getPrice)
					.max()
					.getAsDouble();
			double close = fillsSinceTimeframeStart.get(fillsSinceTimeframeStart.size()-1).getPrice();
			double volume = fillsSinceTimeframeStart.stream()
					.mapToDouble(Fill::getQuantity)
					.sum();
			lastCandle.updateValues(min, max, close, volume);
		}
		
		//calculate TA indicators for the last candle
		lastCandle.setSma(TechnicalAnalysis.calculateSMA(candles, candles.size()-1, 20));
		double[] bbVals = TechnicalAnalysis.calculateBollingerBands(candles, lastCandle.getSma(), candles.size()-1, 20);
		lastCandle.setBbLow(bbVals[0]);
		lastCandle.setBbHigh(bbVals[1]);
		lastCandle.setRsi(TechnicalAnalysis.calculateRSI(candles, candles.size()-1, 20));
		
		System.out.println("Calculated TA indicators:");
		System.out.println("SMA: " + lastCandle.getSma());
		System.out.println("BB low: " + lastCandle.getBbLow() + " BB high: " + lastCandle.getBbHigh());
		System.out.println("RSI: " + lastCandle.getRsi());
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public List<Candle> getCandles1h() {
		return candles1h;
	}

	public void setCandles1h(List<Candle> candles1h) {
		this.candles1h = candles1h;
	}

	public List<Candle> getCandles30m() {
		return candles30m;
	}

	public void setCandles30m(List<Candle> candles30m) {
		this.candles30m = candles30m;
	}

	public List<Candle> getCandles5m() {
		return candles5m;
	}

	public void setCandles5m(List<Candle> candles5m) {
		this.candles5m = candles5m;
	}

	public List<Fill> getFills() {
		return fills;
	}

	public void setFills(List<Fill> fills) {
		this.fills = fills;
	}

	public void addFills(List<Fill> fills) {
		this.fills.addAll(fills);
	}

	
}
