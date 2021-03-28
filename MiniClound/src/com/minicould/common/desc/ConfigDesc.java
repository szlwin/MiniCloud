package com.minicould.common.desc;

import java.util.HashMap;
import java.util.Map;

public class ConfigDesc extends Desc{

	protected Map<String,Desc> valueMap = new HashMap<String,Desc>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7115101255567507017L;

	public void addDesc(String name,Desc desc){
		valueMap.put(name, desc);
	}
	
	public Desc getDesc(String key){
		return valueMap.get(key);
	}
	
	public Map<String,Desc> getAll(){
		return valueMap;
	}
}
