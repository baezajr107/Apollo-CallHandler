package com.Apollo.Control;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.Apollo.Data.Config.BotConfig;
import com.Apollo.Data.Config.PairConfig;
import com.Apollo.Data.PairTicker.PairTicker;

/*
 * This class handles all logic and control for a given trading pair
 * 
 */

public class PairThread implements Runnable{
	private Thread t;
	private boolean stopped = false;
	private PairConfig pairConfig;
	private static BotConfig botConfig;
	private List<PairTicker> pairHistory = new ArrayList<PairTicker>();
	private double currentPairBalance;
	private static double currentBTCBalance;
	   
	public PairThread( PairConfig pairConfig) {
		this.pairConfig = pairConfig;
		System.out.println("Creating pair " +  pairConfig.getPair() );
	}
	   
	public void run() {
		System.out.println("Running " + pairConfig.getPair());

		while(stopped==false) {
			getPairValue();
			
			
			
			// sleep for the set amount of time before cycling again
			try {
				Thread.sleep(botConfig.getRefreshRate()*1000);
			}catch (InterruptedException e) {}
		}
		
	}
	   
	public void start () {
		System.out.println("Starting " +  pairConfig.getPair() );
		if (t == null) {
			t = new Thread (this, pairConfig.getPair());
			t.start ();
		}
	}
	
	public void stop() {
		stopped = true;
	}
	
	//these are stored only in ram for the sake of not keeping stale data
	private void getPairValue() {
			PairTicker returnedPair = ExchangeEngine.getTicker(pairConfig);
			if(returnedPair.getPairName().equals("NULL")) {
				System.out.println("Retrieval of new pair data failed");
				return;
			}
			//pop the oldest data in the list if the max list size has been achieved
			if(pairHistory.size()>=pairConfig.getMaxDataPoints()) {
				pairHistory.remove(0);
			}
			pairHistory.add(returnedPair);
			System.out.println(returnedPair.getPairName() + " data points stored: " + pairHistory.size());
		
	}
	
	
	public static void setBotConfig(BotConfig externalBotConfig) {
		botConfig = externalBotConfig;
	}
	
	public static void setBTCBalance() {
		currentBTCBalance = ExchangeEngine.getBalance("BTC", botConfig).getAvailable();
	}
}
