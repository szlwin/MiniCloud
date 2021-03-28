package com.minicould.node;

import com.minicould.exception.CloundException;

public interface ENode<E,V> extends Node{

	public int STATUS_INIT = 1;
	
	public int STATUS_LOAD = 2;
	
	public int STATUS_PUBLISH = 3;
	
	public int STATUS_UNLOAD = 4;
	
	public V execute(E param) throws CloundException;
}
