package com.minicould.service.data;

import java.util.HashMap;
import java.util.Map;

public class RequestData {

	private Map<String,Object> dataMap = new HashMap<String,Object>();
	
	public void add(String name, Object value){
		dataMap.put(name, value);
	}

	public Object get(String name){
		return dataMap.get(name);
		
	}
	
	public Map<String,Object> getValues(){
		return dataMap;
	}
	
	
}
