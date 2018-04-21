package com.Apollo.Data.Config;

import java.util.List;

//this class contains the overall config for the bot and all trading pairs

public class Config {
	private BotConfig botConfig;
	private List<PairConfig> pairConfigs;
	
	public Config() {
		
	}

	public BotConfig getBotConfig() {
		return botConfig;
	}

	public void setBotConfig(BotConfig botConfig) {
		this.botConfig = botConfig;
	}

	public List<PairConfig> getPairConfigs() {
		return pairConfigs;
	}

	public void setPairConfigs(List<PairConfig> pairConfigs) {
		this.pairConfigs = pairConfigs;
	}
	
	
}
