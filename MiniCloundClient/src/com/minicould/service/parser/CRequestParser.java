package com.minicould.service.parser;

import java.io.IOException;

import com.minicould.service.data.RequestData;
import com.minicould.service.data.ServiceAdapterInfo;
import com.minicould.service.request.RequestInfo;

public interface CRequestParser {
	public RequestInfo parser(String name,int type,int sVersion,RequestData[] requestDataArr, ServiceAdapterInfo serviceAdapterInfo) throws IOException;
}
