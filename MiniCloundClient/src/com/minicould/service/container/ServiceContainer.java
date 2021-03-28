package com.minicould.service.container;

import com.minicould.service.data.ServiceAdapterInfo;

public class ServiceContainer {

	private static ServiceContainer serviceContainer = new ServiceContainer();
	
	public static ServiceContainer getInstance(){
		return serviceContainer;
	}
	
	public void add(String name,int version,ServiceAdapterInfo serviceAdapterData){
		
	}
	
	
	public ServiceAdapterInfo get(String name,int version){
		return null;
		
	}
	
	public boolean isExist(String name,int version){
		return true;
	}
}
