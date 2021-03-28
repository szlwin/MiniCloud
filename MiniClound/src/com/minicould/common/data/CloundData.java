package com.minicould.common.data;

import java.util.HashMap;
import java.util.Map;

public class CloundData extends BaseData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738661276829001844L;
	
	private Map<String,Object> map = new HashMap<String,Object>();
	
	public void setValue(String key,Object value){
		map.put(key, value);
	}

	public Object getValue(String key){
		return map.get(key);
	}
}
