package com.cav.raceconditions.services.wordparsing;

import java.util.Iterator;
import java.util.List;

import com.cav.raceconditions.services.ServiceBase;

public class WordParsingServiceSynchronisedImpl extends ServiceBase  implements WordParsingService{

	String path;
	List<String> words;

	public WordParsingServiceSynchronisedImpl(String path, List<String> words) {
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
		synchronized(words) {
	//	 try {
			Iterator<String> iter = words.iterator();
			while(iter.hasNext()) {
				String word = iter.next();
				if(word.matches("^(?:\\W|\\s)*$")) {
					iter.remove();
				}
			}
		// }catch(Exception e) {
	//		 e.printStackTrace();
		// }
		}
	}

}
