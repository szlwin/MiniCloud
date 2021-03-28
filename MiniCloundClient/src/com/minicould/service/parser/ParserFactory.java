package com.minicould.service.parser;

import java.util.HashMap;
import java.util.Map;

import com.minicould.service.parser.request.CmdRequestParser;
import com.minicould.service.parser.request.ServiceRequestParser;
import com.minicould.service.parser.response.CmdResponseParser;
import com.minicould.service.parser.response.ServiceResponseParser;

public class ParserFactory {

	private static Map<String,Class<? extends CRequestParser>> requestMap = new HashMap<String,Class<? extends CRequestParser>>();
	
	private static Map<String,Class<? extends CResponseParser>> responseMap = new HashMap<String,Class<? extends CResponseParser>>();
	
	private static final ParserFactory parserFactory = new ParserFactory();
	
	static{
		requestMap.put("1_1", ServiceRequestParser.class);
		requestMap.put("1_6", CmdRequestParser.class);
		
		responseMap.put("1_1", ServiceResponseParser.class);
		responseMap.put("1_6", CmdResponseParser.class);
	}
	
	private ParserFactory(){
		
	}
	
	public static ParserFactory getInstance(){
		return parserFactory;
	}
	
	public CRequestParser getRequestParser(int version,int type){
		try {
			return requestMap.get(version+"_"+type).newInstance();
		} catch (Exception e) {

		} 
		return null;
	}
	
	public CResponseParser getResponseParser(int version,int type){
		try {
			return responseMap.get(version+"_"+type).newInstance();
		} catch (Exception e) {

		} 
		return null;
	}
}
