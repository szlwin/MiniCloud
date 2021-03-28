package com.minicould.service.client;

public class RequestIdCreator {

	private long id;
	
	private static final RequestIdCreator requestIdCreator = new RequestIdCreator();
	
	public static RequestIdCreator getInstance(){
		return requestIdCreator;
	}
	
	public synchronized  long getRequestId(){
		id++;
		return id;
	}
}
