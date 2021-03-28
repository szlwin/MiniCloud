package com.minicould.service.request;

import com.minicould.service.data.RequestData;
import com.minicould.service.data.ResponseData;
import com.minicould.service.exception.ConnectionException;

public interface Request {

	public void send(RequestData[] requestData) throws ConnectionException;
	
	public ResponseData getResponse();
}
