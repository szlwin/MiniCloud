package com.minicould.service.parser.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.minicould.service.data.RequestData;
import com.minicould.service.data.ServiceAdapterInfo;
import com.minicould.service.data.ServiceRequestInfo;
import com.minicould.service.parser.CRequestParser;
import com.minicould.service.request.RequestInfo;
import com.minicould.service.util.Util;
import com.report.parse.RequestParser;
import com.report.parse.data.RequestReportData;

public class CmdRequestParser implements CRequestParser {

	public RequestInfo parser(String name, int type, int sVersion,
			RequestData[] requestDataArr, ServiceAdapterInfo serviceAdapterInfo)
			throws IOException {
		RequestInfo requestInfo = new RequestInfo();
		
		requestInfo.setServiceAdapterInfo(serviceAdapterInfo);
		
		
		RequestReportData requestReportData = new RequestReportData();
		
		requestReportData.add("version", 1);
		requestReportData.add("type", type);
		requestReportData.add("id", Util.getRequestId());
		requestReportData.add("codeLength", name.getBytes().length);
		requestReportData.add("code", name);
		requestReportData.add("sVersion", sVersion);
		
		
		requestReportData.add("count", requestDataArr.length);
		
		requestReportData.add("body", parserData(requestDataArr));
		
		RequestParser requestParser = new RequestParser();
		
		byte value[] = requestParser.parse(requestReportData);
		requestInfo.setValue(value);
		requestInfo.setCount(requestDataArr.length);
		return requestInfo;
	}

	private RequestReportData[] parserData(RequestData[] requestDataArr) throws UnsupportedEncodingException {
		
		RequestReportData requestReportDataArr[] = new RequestReportData[requestDataArr.length];
		
		for(int i = 0;i < requestDataArr.length;i++){
			RequestData requestData = requestDataArr[i];
			String str = (String) requestData.get("cmd");
			
			byte[] byteValue = str.getBytes("UTF-8");
			requestReportDataArr[i].add("sLength", byteValue.length);
			requestReportDataArr[i].add("sReport", byteValue);
		}
		
		return requestReportDataArr;
	
	}

}
