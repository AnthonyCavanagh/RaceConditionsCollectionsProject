package com.cav.raceconditions.services.wordparsing;

import java.util.List;

import com.cav.raceconditions.services.ServiceBase;

public class WordParsingServiceCopyOnWriteImpl extends ServiceBase  implements WordParsingService{

	String path;
	List<String> words;

	public WordParsingServiceCopyOnWriteImpl(String path, List<String> words) {
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
		for(String word : words) {
			if(word.matches("^(?:\\W|\\s)*$")) {
				words.remove(word);
			}
		}
	}

}
