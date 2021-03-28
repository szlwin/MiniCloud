package com.minicould.platform.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CloundService {
	private ServerSocket serverSocket;
	
	private ExecutorService pool;
	
	public void init() throws IOException{
		InetSocketAddress socketAddress = new InetSocketAddress("localhost",9093);
		serverSocket = new ServerSocket();
		serverSocket.bind(socketAddress);
		
		pool = Executors.newFixedThreadPool(20);
	}
	
	public void start() throws IOException{
		while(true){
			pool.execute(new CloundThread(serverSocket.accept()));
		}

	}
	
	public static void main(String args[]) throws IOException{
		CloundService cloundService = new CloundService();
		cloundService.init();
		cloundService.start();
	}
}
