package com.minicould.node;

import java.io.InputStream;

import com.minicould.common.desc.Desc;
import com.minicould.exception.CloundException;

public interface Node {

	public int STATUS_INIT = 1;
	
	public int STATUS_START = 2;
	
	public int STATUS_STOP = 3;
	
	public void init() throws CloundException;
	
	public void init(InputStream stream) throws CloundException;
	
	public void remove() throws CloundException;
	
	public int getStatus();
	
	public String getVersion();
	
	public String getName();
	
	public String getNameSpace();
	
	public int getType();
	
	public void setStatus(int status);
	
	public void setVersion(String version);
	
	public void setName(String version);
	
	public void setNameSpace(String version);
	
	public void setInitCompant(SystemCompant compant);
	
	public void setType(int type);
	
	public Desc getDesc();
	
	public void doNext(String name) throws CloundException;
}
