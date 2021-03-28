package com.minicould.service.data;

import java.util.HashMap;
import java.util.Map;

public class ResponseData {

	private int version;
	
	private int type;

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
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
