package com.cav.raceconditions.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import com.cav.raceconditions.model.WordCount;

public class BaseTest {
	
	protected List<WordCount> writeToListAtomic(ConcurrentHashMap<String, LongAdder> map) {
		List <WordCount> words = new ArrayList<WordCount>();
		for(Entry<String, LongAdder> mapEntry : map.entrySet()) {
			String key = mapEntry.getKey();
			LongAdder value = mapEntry.getValue();
			words.add(new WordCount(key, value.intValue()));
		}
		return words;
	}
	
	protected List<WordCount> writeToList(Map<String, Integer> map) {
		List <WordCount> words = new ArrayList<WordCount>();
		for(Entry<String, Integer> mapEntry : map.entrySet()) {
			String key = mapEntry.getKey();
			Integer value = mapEntry.getValue();
			words.add(new WordCount(key, value));
		}
		return words;
	}
	
	protected void writeToFile(List<WordCount> words, String filePath) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath, false));
			for(WordCount wc : words) {
				writer.append(wc.toString());
				writer.append("\r");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

}
