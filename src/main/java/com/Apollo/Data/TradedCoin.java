package com.Apollo.Data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.ccob.bittrex4j.dao.Tick;

@Entity
@Table(name="traded_coins")
public class TradedCoin {

	@Id
	@Column(name="coin_name")
	private String coinName;
	
	@JsonInclude()
	@Transient
	private List<Tick> candles1h;
	
	@JsonInclude()
	@Transient
	private List<Tick> candles30m;
	
	@JsonInclude()
	@Transient
	private List<Tick> candles5m;
	
	public TradedCoin() {
		
	}
	
	public TradedCoin(String name) {
		coinName=name;
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
	
	
	
}
