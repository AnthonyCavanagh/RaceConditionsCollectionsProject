package com.cav.raceconditions.model;

public class WordCount {

	String word;
	Integer wordCount;

	public WordCount(String word, int wordCount) {
		super();
		this.word = word;
		this.wordCount = wordCount;
	}

	public String getWord() {
		return word;
	}


	public Integer getWordCount() {
		return wordCount;
	}

	@Override
	public String toString() {
		return "WordCount [word=" + word + ", wordCount=" + wordCount + "]";
	}

}
