package com.Apollo.Control;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.Apollo.Data.Config.BotConfig;
import com.Apollo.Data.Config.PairConfig;
import com.Apollo.Data.PairBalance.PairBalance;
import com.Apollo.Data.PairBalance.PairBalanceResponse;
import com.Apollo.Data.PairTicker.PairTicker;
import com.Apollo.Data.PairTicker.PairTickerResponse;

//This class contains all the logic necessary to interact with an exchange

public class ExchangeEngine {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static PairTicker getTicker(PairConfig pairConfig) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "https://bittrex.com/api/v1.1/public/getticker?market=";
		ResponseEntity<PairTickerResponse> response = restTemplate.getForEntity(resourceUrl+pairConfig.getPair(), PairTickerResponse.class);
		PairTicker returnPair = new PairTicker("NULL");
		PairTickerResponse responsePayload = response.getBody();
		returnPair =  responsePayload.getResult();
		returnPair.setPairName(pairConfig.getPair());
		return returnPair;
	}
	
	public static PairBalance getBalance(String pair, BotConfig botConfig) {
		PairBalance returnBalance = new PairBalance();
		RestTemplate restTemplate = new RestTemplate();
		
		//create the nonce for hashing
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		long nonce = new Date().getTime();
		
		//create the url
		String resourceUrl = "https://bittrex.com/api/v1.1/account/getbalance?apikey="+ botConfig.getKey() + "&nonce=" + nonce + "&currency=" + pair;
		
		//hash using hmacsha512
		String apisign = hashHmac(resourceUrl,botConfig.getSecret());
		
		//create headers with the apisign
		HttpHeaders headers = new HttpHeaders();
		headers.add("apisign", apisign);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		//post the headers
		ResponseEntity<PairBalanceResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, PairBalanceResponse.class);
		//ResponseEntity<PairBalanceResponse> response = restTemplate.getForEntity(resourceUrl,  PairBalanceResponse.class);
		returnBalance = response.getBody().getResult();
		return returnBalance;
	
	}
	
	private static String hashHmac(String url, String secret) {
	
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(),"HmacSHA512");
			Mac mac = Mac.getInstance("HmacSHA512");
			mac.init(secretKeySpec);
			byte[] hash = mac.doFinal(url.getBytes());
			//convert to hex because why the fuck not
			return bytesToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static String bytesToHex(byte[] bytes) {
		
	    char[] hexChars = new char[bytes.length * 2];
	    
	    for(int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    
	    return new String(hexChars);
	}
}
