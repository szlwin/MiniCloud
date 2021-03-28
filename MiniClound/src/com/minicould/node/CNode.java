package com.minicould.node;

import com.minicould.common.desc.Desc;
import com.minicould.exception.CloundException;

public interface CNode<E,V> extends Node{

	public int STATUS_UNINIT = 0;
	
	public int STATUS_INIT = 1;
	
	public int STATUS_STOP = 2;
	
	public int STATUS_TSTOP = 3;
	
	public int STATUS_RUNNING = 4;
	
	public void execute(E param) throws CloundException;
	
	public V executeV(E param) throws CloundException;
	
	public void load(int type,Desc desc) throws CloundException;
	
}
