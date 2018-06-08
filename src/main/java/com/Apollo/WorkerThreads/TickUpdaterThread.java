package com.Apollo.WorkerThreads;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Apollo.Services.ExchangeService;
import com.github.ccob.bittrex4j.BittrexExchange;
import com.github.ccob.bittrex4j.dao.Fill;
import com.Apollo.Data.TradedCoin;
import com.Apollo.Data.Candle;

/*
 * This class handles all logic and control for a given trading pair
 * 
 */

public class TickUpdaterThread implements Runnable{
	private Thread t;
	private boolean stopped = false;
	private List<TradedCoin> tradedCoins = new ArrayList<TradedCoin>();
	   
	public TickUpdaterThread(List<TradedCoin> tradedCoins) {
		this.tradedCoins = tradedCoins;
	}
	   
	public void run() {

		try(BittrexExchange bittrexExchange = new BittrexExchange()) {


            bittrexExchange.onUpdateExchangeState(updateExchangeState -> {
//                double volume = Arrays.stream(updateExchangeState.getFills())
//                        .mapToDouble(Fill::getQuantity)
//                        .sum();
//
//                System.out.println(String.format("N: %d, %02f volume across %d fill(s) for %s", updateExchangeState.getNounce(),
//                        volume, updateExchangeState.getFills().length, updateExchangeState.getMarketName()));
        		System.out.println(updateExchangeState.getFills().length+" fills for coin: " + updateExchangeState.getMarketName());
            });

            bittrexExchange.connectToWebSocket(() -> {
            	for(TradedCoin coin: tradedCoins) {
            		bittrexExchange.subscribeToExchangeDeltas(coin.getCoinName(), null);
            	}
                bittrexExchange.subscribeToMarketSummaries(null);
            });

            
            
            
//    		while(stopped==false) {
//    			
//    			
//    			// sleep for the set amount of time before cycling again
//
//    		}

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	   
	public void start () {
		if (t == null) {
			t = new Thread (this,"TickUpdaterThread");
			t.start ();
		}
	}
	
	public void stop() {
		stopped = true;
	}
	

	
	
}
