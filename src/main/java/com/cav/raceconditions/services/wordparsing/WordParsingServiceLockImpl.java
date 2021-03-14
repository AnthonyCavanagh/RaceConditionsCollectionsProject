package com.cav.raceconditions.services.wordparsing;

import java.util.Iterator;
import java.util.List;

import com.cav.raceconditions.services.ServiceBase;

public class WordParsingServiceLockImpl extends ServiceBase  implements WordParsingService{

	String path;
	List<String> words;

	public WordParsingServiceLockImpl(String path, List<String> words) {
		super();
		this.path = path;
		this.words = words;
	}

	@Override
	public Object call() throws Exception {
		words = getWordsList(path, words);
		parseWords(words);
		return null;
	}
	
	private void parseWords(List<String> words) {
		for(int index=0; index < words.size(); index++) {
			String word = words.get(index);
			if(word.matches("^(?:\\W|\\s)*$")) {
				words.remove(word);
			}
		}
	}

}
