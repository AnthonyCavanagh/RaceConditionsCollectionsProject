package com.cav.raceconditions.services.wordparsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.cav.raceconditions.collections.ReadWriteList;


public class WordParsingTest {
	
	String [] documents = new String [] {"documents/RandomShortStoryofTheNet1", "documents/RandomShortStoryofTheNet2","documents/RandomShortStoryofTheNet3", "documents/RandomShortStoryofTheNet4", "documents/RandomShortStoryofTheNet5"};
	//String [] documents = new String [] {"documents/RandomShortStoryofTheNet1"};
	
	@Test
	public void parseWordsSynchronized() {
		List <String> words = Collections.synchronizedList(new ArrayList<String>());
		 WordParsingService service = null;
		 ExecutorService executor = Executors.newFixedThreadPool(5);
		 long startTime = System.nanoTime();
			for(String document : documents) {
				service = new   WordParsingServiceSynchronisedImpl(document, words); 
				Future future = executor.submit(service);
			}
			executor.shutdown();
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Empty Queue");
			}
			System.out.println("End process synch "+words.size());
			System.out.println("Time taken concurrent = "+(System.nanoTime()-startTime));
	}
	
	@Test
	public void parseWordsCopyOnWrite() {
		List <String> words = new CopyOnWriteArrayList<String>();
		 WordParsingService service = null;
		 ExecutorService executor = Executors.newFixedThreadPool(5);
		 long startTime = System.nanoTime();
			for(String document : documents) {
				service = new   WordParsingServiceCopyOnWriteImpl(document, words); 
				Future future = executor.submit(service);
			}
			executor.shutdown();
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Empty Queue");
			}
			System.out.println("End process copy on "+words.size());
			System.out.println("Time taken concurrent = "+(System.nanoTime()-startTime));
	}

	@Test
	public void parseWordsLocked() {
		List <String> words = new ReadWriteList();
		 WordParsingService service = null;
		 ExecutorService executor = Executors.newFixedThreadPool(5);
		 long startTime = System.nanoTime();
			for(String document : documents) {
				service = new  WordParsingServiceLockImpl(document, words); 
				Future future = executor.submit(service);
			}
			executor.shutdown();
			try {
				executor.awaitTermination(2, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Empty Queue");
			}
			System.out.println("End process copy on "+words.size());
			System.out.println("Time taken concurrent = "+(System.nanoTime()-startTime));
	}
}
