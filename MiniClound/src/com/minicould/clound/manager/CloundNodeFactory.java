package com.minicould.clound.manager;

public class CloundNodeFactory {

	private static final CloundNodeFactory factory = new CloundNodeFactory();
	private CloundNodeFactory(){
		
	}
	
	
	public static CloundNodeFactory getInstance(){
		return factory;
	}
	
	//public Node create(){
		
	//}
}
