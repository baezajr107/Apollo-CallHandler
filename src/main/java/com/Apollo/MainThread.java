package com.Apollo;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.Apollo.Data.TradedCoin;
import com.Apollo.Services.DatabaseService;
import com.Apollo.WorkerThreads.TickUpdaterThread;


@ComponentScan(basePackages= {"com.Apollo"})
public class MainThread {
	
	public static List<TradedCoin> tradedCoins;
	
    public static void main( String[] args ){
    	
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainThread.class)) {
			DatabaseService databaseService = ctx.getBean(DatabaseService.class);
			tradedCoins = databaseService.getTradedCoins();
			
		}

    	startThreads();

    }
    

    
    private static void startThreads() {
    	//set the static bot config

    	TickUpdaterThread thread = new TickUpdaterThread(tradedCoins);
    	thread.start();
    	//spread out the startup of each pair so the api doesnt get hit by ~50 requests instantly

    	
    }
}
