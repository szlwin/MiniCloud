package com.minicould.service.parser.request;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.minicould.service.data.RequestData;
import com.minicould.service.data.ServiceAdapterInfo;
import com.minicould.service.data.ServiceRequestInfo;
import com.minicould.service.parser.CRequestParser;
import com.minicould.service.request.RequestInfo;
import com.minicould.service.util.Util;

import com.report.parse.RequestParser;
import com.report.parse.data.RequestReportData;

//–≠“È∞Ê±æ∫≈1
public class ServiceRequestParser implements CRequestParser{

	public RequestInfo parser(String name,int type,int sVersion,RequestData[] requestDataArr,ServiceAdapterInfo serviceAdapterInfo) throws IOException{
		
		RequestInfo requestInfo = new RequestInfo();
		
		requestInfo.setServiceAdapterInfo(serviceAdapterInfo);
		
		
		RequestReportData requestReportData = new RequestReportData();
		
		requestReportData.add("version", 1);
		requestReportData.add("type", type);
		requestReportData.add("id", Util.getRequestId());
		requestReportData.add("codeLength", name.getBytes().length);
		requestReportData.add("code", name);
		requestReportData.add("sVersion", sVersion);
		
		ServiceRequestInfo serviceRequestInfo = serviceAdapterInfo.getServiceRequestData();
		
		requestReportData.add("mode", serviceRequestInfo.getMode());
		requestReportData.add("type", serviceRequestInfo.getType());
		requestReportData.add("aType", serviceRequestInfo.isNeedResponse()?2:3);
		
		requestReportData.add("count", requestDataArr.length);
		
		requestReportData.add("body", parserData(requestDataArr));
		
		RequestParser requestParser = new RequestParser();
		
		byte value[] = requestParser.parse(requestReportData);
		requestInfo.setValue(value);
		requestInfo.setCount(requestDataArr.length);
		return requestInfo;
	}
	
	private RequestReportData[] parserData(RequestData[] requestDataArr) throws UnsupportedEncodingException{
		
		RequestReportData requestReportDataArr[] = new RequestReportData[requestDataArr.length];
		
		for(int i = 0;i < requestDataArr.length;i++){
			RequestData requestData = requestDataArr[i];
			Map<String,Object> dataMap = requestData.getValues();
			Set<String> keySet = dataMap.keySet();
			Iterator<String> it = keySet.iterator();
			
			StringBuffer strBuffer = new StringBuffer();
			
			while(it.hasNext()){
				String key = it.next();
				strBuffer.append(key);
				strBuffer.append("=");
				strBuffer.append(dataMap.get(key));
				strBuffer.append(",");
			}
			
			String str = strBuffer.toString();
			str = str.substring(0,str.length()-1);
			byte[] byteValue = str.getBytes("UTF-8");
			requestReportDataArr[i].add("sLength", byteValue.length);
			requestReportDataArr[i].add("sReport", byteValue);
		}
		
		return requestReportDataArr;
	}
}
