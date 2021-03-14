package com.cav.raceconditions.services.wordcount;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.cav.raceconditions.cache.WordCache;
import com.cav.raceconditions.services.ServiceBase;

public class WordCounterCacheServiceIpl extends ServiceBase implements WordCounterService {

	String path = null;

	public WordCounterCacheServiceIpl(String path) {
		super();
		this.path = path;
	}

	@Override
	public Object call() throws Exception {
		List<String> words = getWords(path);
		System.out.println("Word List "+words.size());
		parseWords1(words);
		return null;
	}
	
	private List <String> getWords(String path) {
		return getWordsList(path);
	}
	
	/**
	 * Will also fail  two or more threads can not access get, or put, at the same time 
	 * but one thread can access get, while another thread can access put creating a race condition
	 * 
	 * @author cavan
	 *
	 */
	private void parseWords(List <String> words) {
		for(String word : words) {
			Integer wordCount = WordCache.get(word);
			WordCache.put(word, wordCount);
		}
	}
	
	/**
	 * will pass the get and set is part of the same synchronized block.
	 * @param words
	 */
	private void parseWords1(List <String> words) {
		for(String word : words) {
			WordCache.set(word);
		}
	}

}
