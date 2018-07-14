package com.Apollo.CallHandler.WorkerThreads;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.Apollo.CallHandler.MainThread;
import com.Apollo.CallHandler.Data.Candle;
import com.Apollo.CallHandler.Data.TradedCoin;
import com.github.ccob.bittrex4j.BittrexExchange;
import com.github.ccob.bittrex4j.BittrexExchange.Interval;
import com.github.ccob.bittrex4j.dao.Fill;
import com.github.ccob.bittrex4j.dao.Response;
import com.github.ccob.bittrex4j.dao.Tick;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;


public class MarketInfoUpdaterThread implements Runnable{
	private Thread t;
	static BittrexExchange bittrexExchange;
	
	MessageDigest md;
	//md5 hash of empty string to start with
	private String coinNameHash = "D41D8CD98F00B204E9800998ECF8427E";
	
	   
	public MarketInfoUpdaterThread() {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
	}
	   
	public void run() {

		try {
			bittrexExchange = new BittrexExchange();
		     Logger log_sockets = (Logger) LoggerFactory.getLogger(BittrexExchange.class.getName().concat(".WebSockets"));
		     log_sockets.setLevel(Level.OFF);

            bittrexExchange.onUpdateExchangeState(updateExchangeState -> {
            	TradedCoin coin = MainThread.tradedCoins.get(updateExchangeState.getMarketName());
            	coin.addFills(Arrays.asList(updateExchangeState.getFills()));
            });
            
    		while(true) {
    			Thread.sleep(60000);
    			checkCoinStatus();
    			// sleep for the set amount of time before cycling again

    		}

        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
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
	
	private void checkCoinStatus() {
		System.out.println("checking coin status with coin list size: " + MainThread.tradedCoins.size());

		if(MainThread.tradedCoins != null) {
			boolean connectionNeedsUpdate = false;
			
			//take a hash of the coin names and check for changes
			String coinNames = "";
			for(String name:MainThread.tradedCoins.keySet()) {
				coinNames+=name;
			}
			md.update(coinNames.getBytes());
			String newNameHash = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();
			if(!newNameHash.equals(coinNameHash)) {
				connectionNeedsUpdate = true;
				coinNameHash = newNameHash;
			}
			
			for(TradedCoin coin: MainThread.tradedCoins.values()) {
				if(coin.getCandles5m() == null) {
					System.out.println("getting 5m candles for coin: " + coin.getCoinName());
					Response<Tick[]> bittrexResponse = bittrexExchange.getTicks(coin.getCoinName(), Interval.fiveMin);
					List<Candle> candles = new ArrayList<Candle>();
					Tick[] ticks = bittrexResponse.getResult();
					for(int i=0;i<ticks.length;i++) {
						candles.add(new Candle(ticks[i]));
					}
					coin.setCandles5m(candles);
				}
				if(coin.getCandles30m() == null) {
					System.out.println("getting 30m candles for coin: " + coin.getCoinName());
					Response<Tick[]> bittrexResponse = bittrexExchange.getTicks(coin.getCoinName(), Interval.thirtyMin);
					List<Candle> candles = new ArrayList<Candle>();
					Tick[] ticks = bittrexResponse.getResult();
					for(int i=0;i<ticks.length;i++) {
						candles.add(new Candle(ticks[i]));
					}
					coin.setCandles30m(candles);
				}
				if(coin.getCandles1h() == null) {
					System.out.println("getting 1h candles for coin: " + coin.getCoinName());
					Response<Tick[]> bittrexResponse = bittrexExchange.getTicks(coin.getCoinName(), Interval.hour);
					List<Candle> candles = new ArrayList<Candle>();
					Tick[] ticks = bittrexResponse.getResult();
					for(int i=0;i<ticks.length;i++) {
						candles.add(new Candle(ticks[i]));
					}
					coin.setCandles1h(candles);
				}
			}
			ZonedDateTime now = ZonedDateTime.now();
			if(now.getMinute()%5==0) {
				for(TradedCoin coin:MainThread.tradedCoins.values()) {
					coin.shift5m();
				}
				if(now.getMinute()%30==0) {
					for(TradedCoin coin:MainThread.tradedCoins.values()) {
						coin.shift30m();
					}
					if(now.getMinute()==0) {
						for(TradedCoin coin:MainThread.tradedCoins.values()) {
							coin.shift1h();
						}
					}
				}
			}
			if(connectionNeedsUpdate) {
				updateExchangeConnection();
			}
		}

		
	}
	
	private void updateExchangeConnection() {
       
		try {
			bittrexExchange.disconnectFromWebSocket();
		}catch(NullPointerException e) {
			System.out.println("Not connected to web socket.");
		}
		try {
			bittrexExchange.connectToWebSocket(() -> {
				for(String coin: MainThread.tradedCoins.keySet()) {
					bittrexExchange.subscribeToExchangeDeltas(coin, null);
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
