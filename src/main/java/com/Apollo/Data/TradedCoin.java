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

	public boolean equalsCoin(TradedCoin compareTo) {
		return this.coinName.equals(compareTo.getCoinName());
	}
	
}
