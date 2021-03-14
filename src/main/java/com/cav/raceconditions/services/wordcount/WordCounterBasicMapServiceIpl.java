package com.cav.raceconditions.services.wordcount;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import com.cav.raceconditions.services.ServiceBase;

public class WordCounterBasicMapServiceIpl extends ServiceBase implements WordCounterService {

	Map<String, Integer> wordMap = null;
	String path = null;

	public WordCounterBasicMapServiceIpl(String path, Map<String, Integer> wordMap) {
		super();
		this.wordMap = wordMap;
		this.path = path;
	}

	@Override
	public Object call() throws Exception {
		List<String> words = getWords(path);
		System.out.println("Word List "+words.size());
		parseWords(words);
		return null;
	}
	
	private List <String> getWords(String path) {
		return getWordsList(path);
	}
	
	private void parseWords(List <String> words) {
		for(String word : words) {
			synchronized(wordMap) {
				Integer wordCount = wordMap.get(word);
				if(wordCount == null) {
					wordCount = new Integer(0);
				}
				wordCount++;
				wordMap.put(word, wordCount);
			}
		}
	}

}
