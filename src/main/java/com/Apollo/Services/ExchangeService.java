package com.Apollo.Services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import com.Apollo.Data.Candle;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//This class contains all the logic necessary to interact with an exchange
import com.fasterxml.jackson.databind.ObjectReader;

public class ExchangeService {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static List<Candle> getTickers(String name, String interval) {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "https://bittrex.com/Api/v2.0/pub/market/GetTicks?marketName="+ name +"&tickInterval="+interval;
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(resourceUrl, JsonNode.class);
		ObjectMapper mapper = new ObjectMapper();
		ObjectReader reader = mapper.readerFor(new TypeReference<List<Candle>>() {
		});

		try {
			List<Candle> tickerList = reader.readValue(response.getBody().path("result"));
			return tickerList;
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
//	public static PairBalance getBalance(String pair, BotConfig botConfig) {
//		PairBalance returnBalance = new PairBalance();
//		RestTemplate restTemplate = new RestTemplate();
//		
//		//create the nonce for hashing
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//		long nonce = new Date().getTime();
//		
//		//create the url
//		String resourceUrl = "https://bittrex.com/api/v1.1/account/getbalance?apikey="+ botConfig.getKey() + "&nonce=" + nonce + "&currency=" + pair;
//		
//		//hash using hmacsha512
//		String apisign = hashHmac(resourceUrl,botConfig.getSecret());
//		
//		//create headers with the apisign
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("apisign", apisign);
//		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
//		
//		//post the headers
//		ResponseEntity<PairBalanceResponse> response = restTemplate.exchange(resourceUrl, HttpMethod.GET, entity, PairBalanceResponse.class);
//		//ResponseEntity<PairBalanceResponse> response = restTemplate.getForEntity(resourceUrl,  PairBalanceResponse.class);
//		returnBalance = response.getBody().getResult();
//		return returnBalance;
//	
//	}
	
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
