package com.minicould.service.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.minicould.service.client.Config;
import com.minicould.service.client.RequestIdCreator;
import com.minicould.service.client.TaskIdCreator;
import com.minicould.service.config.ClientConfig;
import com.minicould.service.container.ServiceContainer;
import com.minicould.service.data.RequestData;
import com.minicould.service.data.ServiceAdapterInfo;
import com.minicould.service.parser.CRequestParser;
import com.minicould.service.parser.CResponseParser;
import com.minicould.service.parser.ParserFactory;
import com.minicould.service.request.RequestInfo;
import com.minicould.service.response.ResponseInfo;
import com.report.parse.data.ResponseReportData;

public class Util {

	public static boolean isExist(String name,int version){
		return ServiceContainer.getInstance().isExist(name, version);
	}
	
	
	public static ServiceAdapterInfo getServiceAdapterInfo(String name,int version){
		return ServiceContainer.getInstance().get(name, version);
	}
	
	public static RequestInfo[] parser(String name,int sVersion,int sType,RequestData[] requestDataArr) throws IOException{
		
		List<RequestInfo> requestInfoList = new ArrayList<RequestInfo>();
		
		int max = ClientConfig.getInstance().getMax();
		
		int length = requestDataArr.length;
		
		int count = length / max;
		
		if(count == 0){
			count = 1;
		}
		
		int limit= length % max;
		
		CRequestParser parser = ParserFactory.getInstance().getRequestParser(
				ClientConfig.getInstance().getVersion(), sType);
		
		ServiceAdapterInfo serviceAdapterInfo = ServiceContainer.getInstance().get(name, sVersion);
		
		for(int i = 0; i < count;i++){
			
			RequestData[] requestDataArrT =  new RequestData[max];
			System.arraycopy(requestDataArr, i*max, requestDataArrT, 0, max);
			
			
			RequestInfo requestInfo = parser.parser(name, sType, sVersion, requestDataArrT,serviceAdapterInfo);
			
			requestInfoList.add(requestInfo);
		}
		
		if(limit > 0){
			
			RequestData[] requestDataArrT =  new RequestData[limit];
			System.arraycopy(requestDataArr, count*max, requestDataArrT, 0, limit);
			
			RequestInfo requestInfo = parser.parser(name, sType, sVersion, requestDataArrT,serviceAdapterInfo);
			
			requestInfoList.add(requestInfo);
		}
		
		return (RequestInfo[]) requestInfoList.toArray();
		
	}
	
	public static ResponseInfo parserResonseData(ResponseReportData responseReportData) throws IOException{
		int version = (Integer) responseReportData.get("version");
		int type = (Integer) responseReportData.get("type");
		CResponseParser responseParser = ParserFactory.getInstance().getResponseParser(version, type);
		return responseParser.parser(responseReportData);
	}
	
	
	public static long getRequestId(){
		return RequestIdCreator.getInstance().getRequestId();
	}
	
	public static long getTaskId(){
		return TaskIdCreator.getInstance().getTaskId();
	}
	
	public static RequestInfo parserCmd(RequestData[] requestDataArr) throws IOException{
		
		CRequestParser parser = ParserFactory.getInstance().getRequestParser(
				ClientConfig.getInstance().getVersion(), Config.TYPE_CMD);
		
		RequestInfo requestInfo = parser.parser("cmd", Config.TYPE_CMD, 1, requestDataArr, null);
	
		return requestInfo;
		
	}
}
