package com.minicould.service.port;

import java.io.IOException;

import com.minicould.service.client.Config;
import com.minicould.service.data.RequestData;
import com.minicould.service.request.RequestInfo;
import com.minicould.service.response.ResponseInfo;
import com.minicould.service.util.Util;
import com.report.parse.ResponseParser;
import com.report.parse.data.ResponseReportData;

public class Task {

	private long id;

	private ResponseInfo[] responseInfoArr;
	
	public long getId() {
		return id;
	}
	
	public Task(){
		this.id = Util.getTaskId();
	}
	
	public void execute(String name,int version,RequestData[] rDataArray) throws IOException{
		
		RequestInfo[] requestInfoArr = Util.parser(name,version, Config.TYPE_SERVICE, rDataArray);
		
		responseInfoArr = new ResponseInfo[requestInfoArr.length];
		
		for(int i = 0; i < rDataArray.length;i++){
			RequestInfo requestInfo = requestInfoArr[i];
			SocketSend socketSend = new SocketSend();
			socketSend.send(requestInfo);
			byte[] byteArray = socketSend.get();
			ResponseInfo responseInfo = parse(byteArray);
			responseInfoArr[i] = responseInfo;
		}
	}
	
	public ResponseInfo get(){
		return responseInfoArr[0];
	}
	
	private ResponseInfo parse(byte[] byteArray) throws IOException{
		ResponseParser responseParser = new ResponseParser();
		
		ResponseReportData responseReportData = responseParser.parser(byteArray);

		return Util.parserResonseData(responseReportData);
	}
}
