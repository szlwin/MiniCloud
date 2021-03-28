package com.minicould.service.request;

import com.minicould.service.data.ServiceAdapterInfo;

public class RequestInfo {

	private ServiceAdapterInfo serviceAdapterInfo;
	
	private int count;
	
	private Object value;

	private long id;
	
	public RequestInfo(){
		
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ServiceAdapterInfo getServiceAdapterInfo() {
		return serviceAdapterInfo;
	}

	public void setServiceAdapterInfo(ServiceAdapterInfo serviceAdapterInfo) {
		this.serviceAdapterInfo = serviceAdapterInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
