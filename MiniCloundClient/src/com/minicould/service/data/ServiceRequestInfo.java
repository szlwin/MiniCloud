package com.minicould.service.data;

public class ServiceRequestInfo {

	//����ʽ
	private int type;
	
	//����ģʽ
	private int mode;
	
	private boolean needResponse;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public boolean isNeedResponse() {
		return needResponse;
	}

	public void setNeedResponse(boolean needResponse) {
		this.needResponse = needResponse;
	}
	
	
}
