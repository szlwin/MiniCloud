package com.minicould.service.client;

public class TaskIdCreator {

	private long id;
	
	private static TaskIdCreator requestIdCreator = new TaskIdCreator();
	
	public static TaskIdCreator getInstance(){
		return requestIdCreator;
	}
	
	public synchronized  long getTaskId(){
		id++;
		return id;
	}
}
