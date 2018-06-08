package com.Apollo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

import com.Apollo.Data.TradedCoin;
import com.Apollo.Repositories.TradedCoinsRepository;

@Component
public class DatabaseService{
	@Autowired
	private TradedCoinsRepository tradedCoinsRepository;
	
	
	public void saveTradedCoin(TradedCoin coin) {
		tradedCoinsRepository.save(coin);
	}
	
	public List<TradedCoin> getTradedCoins(){
		return tradedCoinsRepository.findAll();
	}

}
