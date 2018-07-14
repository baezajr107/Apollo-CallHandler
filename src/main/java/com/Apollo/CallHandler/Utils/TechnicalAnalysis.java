package com.Apollo.CallHandler.Utils;

import java.util.List;
import java.util.stream.Collectors;

import com.Apollo.CallHandler.Data.Candle;

public class TechnicalAnalysis {

	public static double calculateSMA(List<Candle> candles, int index, int seriesLength) {
		List<Candle> candleSubset = subsetList(candles,index,seriesLength);
		
		double sum = candleSubset.stream().collect(Collectors.summingDouble(Candle::getClose));
		return sum/candleSubset.size();
	}
	
	public static double[] calculateBollingerBands(List<Candle> candles, double sma, int index, int seriesLength) {
		List<Candle> candleSubset = subsetList(candles,index,seriesLength);
		
		//calculate the standard deviation for the candles
		double stdev = Math.sqrt(candleSubset.stream()
				.map(i -> Math.pow((i.getClose()-sma), 2))
				.collect(Collectors.summingDouble(d->d))/candleSubset.size());

		
		return  new double[]{(sma-stdev*2),(sma+stdev*2)};
		
	}
	
	public static double calculateRSI(List<Candle> candles, int index, int seriesLength) {
		List<Candle> candleSubset = subsetList(candles,index,seriesLength);
		double avgGain = candleSubset.stream()
				.filter(i -> i.getOpen()<=i.getClose())
				.map(i -> i.getClose()-i.getOpen())
				.collect(Collectors.summingDouble(d->d))/candleSubset.size();
		double avgLoss = candleSubset.stream()
				.filter(i -> i.getOpen()>i.getClose())
				.map(i -> i.getOpen() - i.getClose())
				.collect(Collectors.summingDouble(d->d))/candleSubset.size();
		return 100-100/(1+(avgGain/avgLoss));
	}
	
	private static List<Candle> subsetList(List<Candle> candles, int index, int seriesLength){
		return candles.stream()
				.skip((index>=seriesLength)?index-seriesLength:0)
				.limit(seriesLength)
				.collect(Collectors.toList());
	}
}
