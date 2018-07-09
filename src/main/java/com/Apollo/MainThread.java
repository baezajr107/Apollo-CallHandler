package com.Apollo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.Apollo.Data.TradedCoin;
import com.Apollo.WorkerThreads.CoinManagerThread;
import com.Apollo.WorkerThreads.MarketInfoUpdaterThread;


@SpringBootApplication(scanBasePackages="com.Apollo")
public class MainThread implements CommandLineRunner{
	
	public static Map<String,TradedCoin> tradedCoins;
	
	@Autowired
	CoinManagerThread coinManager;
	
    public static void main( String[] args )  throws InterruptedException {
        SpringApplication.run(MainThread.class, args);
    	


    }
    

    
    private void startThreads() {

    	coinManager.start();

    	MarketInfoUpdaterThread marketInfoUpdater = new MarketInfoUpdaterThread();
    	marketInfoUpdater.start();
    	//spread out the startup of each pair so the api doesnt get hit by ~50 requests instantly

    	
    }



	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		startThreads();
    	while(true) {
    		Thread.sleep(60000);
    		try {
	    		for(TradedCoin coin:tradedCoins.values()) {
	    			System.out.println("=====================================================");
	    			System.out.println("Coin: " + coin.getCoinName());
	    			System.out.println("Fill count: " + coin.getFills().size());
	    			System.out.println("5m candle count: " + coin.getCandles5m().size());
	    		}
    		}catch(Exception e) {
    			System.out.println("Failed to read coin info.");
    		}
    	}
	}
}
