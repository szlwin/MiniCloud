package com.minicould.service.request;

import com.minicould.service.data.ResponseData;

public abstract class AbstractRequest implements Request{

	private ResponseData responseData;

	public ResponseData getResponse() {
		// TODO Auto-generated method stub
		return responseData;
	}

}
