package com.cav.raceconditions.services.wordcount;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

import org.junit.jupiter.api.Test;

import com.cav.raceconditions.cache.WordCache;
import com.cav.raceconditions.model.WordCount;
import com.cav.raceconditions.services.BaseTest;
/**
 * ConcurrentHashMap does not handle race conditions where the same value for the same key is being red/written to. 
 * Put and get does not handle race conditions
 * 
 * @author cavan
 *
 */
public class WordCounterServiceTest extends BaseTest {

	String [] documents = new String [] {"documents/RandomShortStoryofTheNet1", "documents/RandomShortStoryofTheNet2","documents/RandomShortStoryofTheNet3", "documents/RandomShortStoryofTheNet4", "documents/RandomShortStoryofTheNet5"};
	//String [] documents = new String [] {"documents/RandomShortStoryofTheNet1","documents/RandomShortStoryofTheNet2","documents/RandomShortStoryofTheNet3"};
	/*
	 * ConcurrentHashMap does not handle race conditions.
	 */
	@Test
	public void testWordCounter() {
		System.out.println("Start test");
		ConcurrentHashMap<String, Integer> wordMap = new ConcurrentHashMap<String, Integer>();
		WordCounterService service = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(String document : documents) {
			service = new  WordCounterServiceIpl(document, wordMap); 
			Future future = executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty Queue");
		}
		List<WordCount> words = writeToList(wordMap);
		Comparator<WordCount> wordCountComparator = Comparator.comparing(WordCount::getWordCount);
		Collections.sort(words, wordCountComparator);
		writeToFile(words, "C:/testData/wordCountMulipleSorted.txt");
	}
	
	
	
	@Test
	public void testWordCounterSynchronize() {
		System.out.println("Start test");
		long startTime = System.nanoTime();
		ConcurrentHashMap<String, Integer> wordMap = new ConcurrentHashMap<String, Integer>();
		WordCounterService service = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(String document : documents) {
			service = new  WordCountersynchronizedServiceIpl(document, wordMap); 
			Future future = executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty Queue");
		}
		System.out.println("Time taken Synchronize = "+(System.nanoTime()-startTime));
		List<WordCount> words = writeToList(wordMap);
		Comparator<WordCount> wordCountComparator = Comparator.comparing(WordCount::getWordCount);
		Collections.sort(words, wordCountComparator);
		System.out.println("Write to file");
		writeToFile(words, "C:/testData/wordCountMulipleSorted.txt");
	}
	
	@Test
	public void testWordCounterConcurrent() {
		System.out.println("Start test");
		long startTime = System.nanoTime();
		ConcurrentHashMap<String, LongAdder> wordMap = new ConcurrentHashMap<String, LongAdder>();
		WordCounterService service = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(String document : documents) {
			service = new WordCountersConcurrentServiceIpl(document, wordMap); 
			Future future = executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty Queue");
		}
		System.out.println("Time taken concurrent = "+(System.nanoTime()-startTime));
		List<WordCount> words = writeToListAtomic(wordMap);
		Comparator<WordCount> wordCountComparator = Comparator.comparing(WordCount::getWordCount);
		Collections.sort(words, wordCountComparator);
		System.out.println("Write to file");
		writeToFile(words, "C:/testData/wordCountMulipleSorted.txt");
	}
	
	@Test
	public void testWordCounterCache() {
		System.out.println("Start test");
		WordCounterService service = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(String document : documents) {
			service = new  WordCounterCacheServiceIpl(document); 
			Future future = executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty Queue");
		}
		System.out.println("Write to list");
		List<WordCount> words = writeToList(WordCache.wordCountmap);
		Comparator<WordCount> wordCountComparator = Comparator.comparing(WordCount::getWordCount);
		Collections.sort(words, wordCountComparator);
		System.out.println("Write to file");
		writeToFile(words, "C:/testData/wordCountMulipleSorted.txt");
	}
	
	//Uses a HashMap, syronizes on the collection will handle race conditions.
	@Test
	public void testWordCounterBasicMap() {
		System.out.println("Start test");
		Map<String, Integer> wordMap = new HashMap<String, Integer>();
		WordCounterService service = null;
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for(String document : documents) {
			service = new  WordCounterBasicMapServiceIpl(document, wordMap); 
			Future future = executor.submit(service);
		}
		executor.shutdown();
		try {
			executor.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Empty Queue");
		}
		System.out.println("Write to list");
		List<WordCount> words = writeToList(wordMap);
		Comparator<WordCount> wordCountComparator = Comparator.comparing(WordCount::getWordCount);
		Collections.sort(words, wordCountComparator);
		System.out.println("Write to file");
		writeToFile(words, "C:/testData/wordCountMulipleSorted.txt");
	}
	

}
