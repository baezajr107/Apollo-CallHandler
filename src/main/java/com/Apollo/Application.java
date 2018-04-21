package com.Apollo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.Apollo.Control.PairThread;
import com.Apollo.Data.Config.Config;
import com.Apollo.Data.Config.PairConfig;
import com.Apollo.Data.PairTicker.PairTicker;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {
	private static List<PairThread> threadList = new ArrayList<PairThread>();
	
    public static void main( String[] args ){
    	
    	Config config = generateConfigFromFile();
    	if( config.getPairConfigs().size() >= config.getBotConfig().getRefreshRate()) {
    		System.out.println("Too many pairs for the refresh rate, risking ban for overly frequent requests. please reduce number of pairs or increase refresh rate");
    	}
    	startThreads(config);
    	threadList.get(0).stop();

    }
    
    private static Config generateConfigFromFile() {
    	ObjectMapper objectMapper = new ObjectMapper();
    	try {
    		byte[] jsonData = Files.readAllBytes(Paths.get("config.json"));
	    	return objectMapper.readValue(jsonData, Config.class);
		} catch (IOException e) {
			System.out.println("could not read config file");
			e.printStackTrace();
		}
    	System.out.println("Could not parse config file into object");
    	return new Config();
    }
    
    private static void startThreads(Config config) {
    	//set the static bot config
    	PairThread.setBotConfig(config.getBotConfig());
    	PairThread.setBTCBalance();
    	for(PairConfig pair: config.getPairConfigs()) {
        	PairThread thread = new PairThread(pair);
        	thread.start();
        	threadList.add(thread);
        	//spread out the startup of each pair so the api doesnt get hit by ~50 requests instantly
        	try {
				Thread.sleep(config.getBotConfig().getRefreshRate()/config.getPairConfigs().size()*1000);
			}catch (InterruptedException e) {}
    	}
    }
}
