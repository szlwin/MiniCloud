package com.minicould.common.result;

import com.minicould.common.data.BaseData;

public class Result extends BaseData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 57220099235316714L;
	
	private boolean isSuccess;
	
	private String errorCode;
	
	private String errorMsg;

	private Object value;
	
	private String version;
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object getValue) {
		this.value = getValue;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
