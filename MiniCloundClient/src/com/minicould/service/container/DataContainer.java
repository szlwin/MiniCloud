package com.minicould.service.container;

import com.minicould.service.data.ResponseData;

public class DataContainer {

	
	private static DataContainer dataContainer = new DataContainer();
	
	public static DataContainer getInstance(){
		return dataContainer;
	}
	
	public void add(long id,ResponseData responseData){
		
	}
	
	public ResponseData get(long id){
		return null;
		
	}
}
