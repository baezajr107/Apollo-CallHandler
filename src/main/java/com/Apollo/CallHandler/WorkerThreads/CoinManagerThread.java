package com.Apollo.CallHandler.WorkerThreads;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Apollo.CallHandler.MainThread;
import com.Apollo.CallHandler.Data.TradedCoin;
import com.Apollo.CallHandler.Repositories.TradedCoinsRepository;
import com.github.ccob.bittrex4j.BittrexExchange;

@Service
public class CoinManagerThread implements Runnable{
	private Thread t;
	
	@Autowired
	private TradedCoinsRepository tradedCoinsRepo;
	
	public CoinManagerThread() {
	}
	
	public void run() {
		if(MainThread.tradedCoins == null) {
			MainThread.tradedCoins = new HashMap<String,TradedCoin>();
			List<TradedCoin> tradedCoinsList = tradedCoinsRepo.findAll();
			for(TradedCoin coin:tradedCoinsList) {
				System.out.println("Inserting to coin map with name: " + coin.getCoinName());
				MainThread.tradedCoins.put(coin.getCoinName(), coin);
			}
			System.out.println(MainThread.tradedCoins.size());
		}
		
		
	}
	
	public void start () {
		if (t == null) {
			t = new Thread (this,"TickUpdaterThread");
			t.start ();
		}
	}
}
