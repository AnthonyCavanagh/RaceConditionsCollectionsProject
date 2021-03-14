package com.cav.raceconditions.services.wordcount;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

import com.cav.raceconditions.services.ServiceBase;

public class WordCountersConcurrentServiceIpl extends ServiceBase implements WordCounterService {

	ConcurrentHashMap<String, LongAdder> wordMap = null;
	String path = null;

	public WordCountersConcurrentServiceIpl(String path, ConcurrentHashMap<String, LongAdder> wordMap) {
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
			wordMap.computeIfAbsent(word, k -> new LongAdder()).increment();
		}
	}

}
