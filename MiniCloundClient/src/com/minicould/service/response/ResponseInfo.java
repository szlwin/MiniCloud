package com.minicould.service.response;

import com.minicould.service.data.ResponseData;

public class ResponseInfo {

	private boolean isSuccess;
	
	private int errorCode;
	
	private String errorMsg;
	
	private ResponseData[] responseData;
	
	private short status;
	
	private long id;
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ResponseData[] getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData[] responseData) {
		this.responseData = responseData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}
	
	
}
