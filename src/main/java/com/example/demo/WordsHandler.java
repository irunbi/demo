package com.example.demo;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

@Service
public class WordsHandler{
	
	private Map<String,Integer> frequencyWords = new ConcurrentHashMap<String,Integer>();
	
	public void addWords(String commaSeperatedString) {
		
		List<String> words = Stream.of(commaSeperatedString.split(","))
				.collect(Collectors.toList());
		
		words.forEach(key -> frequencyWords.merge(key, 1, Integer::sum));		
	}
	
	public String getStats() {		
		if(frequencyWords.isEmpty()) {
			return "";
		}
		
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("top5:\n");
		
		LinkedList<Map.Entry<String, Integer>> list = new LinkedList<>(frequencyWords.entrySet());
		Comparator<Map.Entry<String, Integer>> comparator = Comparator.comparing(Map.Entry::getValue);
		Collections.sort(list, comparator.reversed());
		
		
		for(int i=0; i<5 && i < list.size(); i++) {
			
			Map.Entry<String, Integer> word = list.get(i);
			stringBuilder.append(word.getKey()).append(" ").append(word.getValue()).append("\n");
		}
		
		int leaset = list.getLast().getValue();
		int median = calculateMedian(list);
		
		stringBuilder.append("\n").append("leaset: ").append(leaset).append("\n");
		stringBuilder.append("\n").append("median: ").append(median).append("\n");

		return stringBuilder.toString();
	}
	
	private int calculateMedian(LinkedList<Map.Entry<String, Integer>> list) {
		int median = list.stream()
				.map(v -> v.getValue())
				.collect(Collectors.collectingAndThen(
		                 Collectors.toList(),
		                 freq -> {
		                    int count = freq.size();
		                    if (count % 2 == 0) { 
		                        return (freq.get(count / 2 - 1) + freq.get(count / 2)) / 2;
		                    } else { 
		                        return freq.get(count / 2);
		                    }
		                }));
		return median;
	}
	
}