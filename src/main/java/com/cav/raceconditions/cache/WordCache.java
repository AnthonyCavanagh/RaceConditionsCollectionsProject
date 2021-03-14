package com.cav.raceconditions.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Will also fail  two or more threads can not access get, or put, at the same time 
 * but one thread can access get, while another thread can access put creating a race condition
 * 
 * @author cavan
 *
 */
public class WordCache {

	public static ConcurrentHashMap<String, Integer> wordCountmap = new ConcurrentHashMap <String, Integer> ();
	
	public static synchronized Integer get(String word) {
		Integer wordCounter = wordCountmap.get(word);
		if(wordCounter == null) {
			return 0;
		}
		return wordCounter;
	}
	
	public static synchronized void  put(String word, Integer wordCount) {
		wordCount = wordCount+1;
		wordCountmap.put(word, wordCount);
	}
	
	public static synchronized void set(String word) {
		Integer wordCount = wordCountmap.get(word);
		if(wordCount == null) {
			wordCount = 0;
		}
		wordCount = wordCount+1;
		wordCountmap.put(word, wordCount);
	}
}
