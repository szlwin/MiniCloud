package com.minicould.service.parser.response;

import java.io.IOException;

import com.minicould.service.data.ResponseData;
import com.minicould.service.parser.CResponseParser;
import com.minicould.service.response.ResponseInfo;
import com.report.parse.data.ResponseReportData;

//Ð­Òé°æ±¾ºÅ1
public class ServiceResponseParser implements CResponseParser{

	public ResponseInfo parser(ResponseReportData responseReportData)
			throws IOException {
		
		long id = (Long) responseReportData.get("id");
		short result = (Short) responseReportData.get("result");
		
		ResponseInfo responseInfo = new ResponseInfo();
		responseInfo.setId(id);
		
		if(result == 1){
			responseInfo.setSuccess(true);
			parserSuccess(responseInfo,responseReportData);
		}else{
			responseInfo.setSuccess(false);
			parserFail(responseInfo,responseReportData);
		}
		return responseInfo;
	}

	private void parserSuccess(ResponseInfo responseInfo,ResponseReportData responseReportData){
		short status = (Short) responseReportData.get("status");
		responseInfo.setStatus(status);
		
		int count = (Integer) responseReportData.get("rCount");
		
		if(count > 0){
			ResponseReportData[] responseReportDataArray =  (ResponseReportData[]) responseReportData.get("body");
			ResponseData responseDataArr[] = parserServiceData(responseReportDataArray);
			responseInfo.setResponseData(responseDataArr);
		}
	}
	
	private void parserFail(ResponseInfo responseInfo,ResponseReportData responseReportData){
		int errorCode = (Integer) responseReportData.get("errorCode");
		String errorMsg = (String) responseReportData.get("errorMsg");
		
		responseInfo.setErrorCode(errorCode);
		responseInfo.setErrorMsg(errorMsg);
	}
	
	private ResponseData[] parserServiceData(ResponseReportData[] responseReportDataArray){
		
		ResponseData responseDataArr[] = new ResponseData[responseReportDataArray.length];
		
		for(int i = 0; i <responseReportDataArray.length;i++){
			String str  =  (String) responseReportDataArray[i].get("sReport");
			responseDataArr[i] = parserString(str);
		}
		return responseDataArr;
	}
	
	private ResponseData parserString(String str){
		String strArray[] = str.split(",");
		
		ResponseData responseData = new ResponseData();
		
		for(String strData : strArray){
			String strDataArray[] = strData.split("=");
			responseData.add(strDataArray[0], strDataArray[1]);
		}
		return responseData;
	}
}
