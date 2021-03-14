package com.cav.raceconditions.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServiceBase {
	
	protected List <String> getWordsList(String path) {
		InputStream in = null;
		BufferedReader bufferedReader = null;
		List <String> words = new ArrayList<String>();
		String line = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(path);
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			while (null != (line = bufferedReader.readLine())) {
				String[] fields = line.split(" ");
				for(String field : fields) {
					words.add(field);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return words;
	}
	
	protected List <String> getWordsList(String path, List <String> words) {
		InputStream in = null;
		BufferedReader bufferedReader = null;
		String line = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream(path);
			bufferedReader = new BufferedReader(new InputStreamReader(in));
			while (null != (line = bufferedReader.readLine())) {
				String[] fields = line.split(" ");
				for(String field : fields) {
					words.add(field);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return words;
	}
	

}
