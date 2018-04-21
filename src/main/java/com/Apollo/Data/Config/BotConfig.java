package com.Apollo.Data.Config;

//this class contains the config settings for the bot itself

public class BotConfig {
	private String key;
	private String secret;
	private int refreshRate;

	
	public BotConfig() {
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getRefreshRate() {
		return refreshRate;
	}

	public void setRefreshRate(int refreshRate) {
		this.refreshRate = refreshRate;
	}
	
	
}
