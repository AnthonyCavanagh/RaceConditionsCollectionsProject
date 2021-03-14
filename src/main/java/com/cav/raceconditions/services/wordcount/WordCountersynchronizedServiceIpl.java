package com.cav.raceconditions.services.wordcount;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import com.cav.raceconditions.services.ServiceBase;

public class WordCountersynchronizedServiceIpl extends ServiceBase implements WordCounterService {

	ConcurrentHashMap<String, Integer> wordMap = null;
	String path = null;



	public WordCountersynchronizedServiceIpl(String path, ConcurrentHashMap<String, Integer> wordMap) {
		super();
		this.wordMap = wordMap;
		this.path = path;
	}


	@Override
	public Object call() throws Exception {
		List<String> words = getWords(path);
		System.out.println("Word List "+words.size());
		parseWords(words, wordMap);
		return null;
	}
	
	private List <String> getWords(String path) {
		return getWordsList(path);
	}
	
	

	/**
	 * This will work as it will synchronized on the object, in this case a collection, which will synchronized on the get and put.
	 * @param words
	 * @param wordMap
	 */
	private void parseWords(List <String> words, ConcurrentHashMap<String, Integer> wordMap) {
		for(String word : words) {
			synchronized(wordMap) {
				Integer wordCount = (wordMap.get(word) == null) ? 0 :wordMap.get(word);
				wordCount++;
				wordMap.put(word, wordCount);
			}
			
			
		}
	}
	/*
	private void parseWords(List <String> words, ConcurrentHashMap<String, Integer> wordMap) {
		for(String word : words) {
			wordMap.computeIfAbsent(word, k -> new LongAdder()).increment();
		}
	}*/
	

}
