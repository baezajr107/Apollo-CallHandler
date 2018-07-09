package com.Apollo.Data;

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
		List<Fill> fillsLast5m = fills.stream()
		.filter(fill -> fill.getTimeStamp().isAfter(nowMinus5))
		.collect(Collectors.toList());
		
		double min = fillsLast5m.stream()
				.mapToDouble(Fill::getPrice)
				.min()
				.getAsDouble();
		double max = fillsLast5m.stream()
				.mapToDouble(Fill::getPrice)
				.max()
				.getAsDouble();
		double close = fillsLast5m.get(fillsLast5m.size()-1).getPrice();
		double volume = fillsLast5m.stream()
				.mapToDouble(Fill::getQuantity)
				.sum();
		
		candles5m.get(candles5m.size()-1).updateValues(min, max, close, volume);
		candles5m.add(new Candle(close));
		
	}
	
	public void shift30m() {
		System.out.println("Shifting 30 minute candles back for coin: " + coinName);
		ZonedDateTime nowMinus30 = ZonedDateTime.now().minusMinutes(30);
		List<Fill> fillsLast30m = fills.stream()
		.filter(fill -> fill.getTimeStamp().isAfter(nowMinus30))
		.collect(Collectors.toList());
		
		double min = fillsLast30m.stream()
				.mapToDouble(Fill::getPrice)
				.min()
				.getAsDouble();
		double max = fillsLast30m.stream()
				.mapToDouble(Fill::getPrice)
				.max()
				.getAsDouble();
		double close = fillsLast30m.get(fillsLast30m.size()-1).getPrice();
		double volume = fillsLast30m.stream()
				.mapToDouble(Fill::getQuantity)
				.sum();
		
		candles30m.get(candles30m.size()-1).updateValues(min, max, close, volume);
		candles30m.add(new Candle(close));
	}
	
	public void shift1h() {
		System.out.println("Shifting 1 hour candles back for coin: " + coinName);
		ZonedDateTime nowMinus1h = ZonedDateTime.now().minusHours(1);
		List<Fill> fillsLast1h = fills.stream()
		.filter(fill -> fill.getTimeStamp().isAfter(nowMinus1h))
		.collect(Collectors.toList());
		
		double min = fillsLast1h.stream()
				.mapToDouble(Fill::getPrice)
				.min()
				.getAsDouble();
		double max = fillsLast1h.stream()
				.mapToDouble(Fill::getPrice)
				.max()
				.getAsDouble();
		double close = fillsLast1h.get(fillsLast1h.size()-1).getPrice();
		double volume = fillsLast1h.stream()
				.mapToDouble(Fill::getQuantity)
				.sum();
		
		candles1h.get(candles1h.size()-1).updateValues(min, max, close, volume);
		candles1h.add(new Candle(close));
		fills.clear();
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
