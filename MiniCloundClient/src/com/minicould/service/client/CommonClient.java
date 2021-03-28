package com.minicould.service.client;

import java.io.IOException;

import com.minicould.service.data.RequestData;
import com.minicould.service.exception.ConnectionException;
import com.minicould.service.port.Task;
import com.minicould.service.response.ResponseInfo;
import com.minicould.service.util.Util;

public class CommonClient {

	private int version;
	
	private ResponseInfo responseInfo;
	
	public void send(String name,int version,RequestData rData) throws ConnectionException{
		send(name,version,new RequestData[]{rData});
	}
	
	//private void send(String name,int version,List<RequestData> rDataList) throws ConnectionException{
	//	send(name,version,(RequestData[])rDataList.toArray());
	//}
	
	private void send(String name,int version,RequestData[] rDataArray) throws ConnectionException{
		
		if(!Util.isExist(name, version)){
			
		}
		
		Task task = new Task();
		try {
			task.execute(name, version, rDataArray);
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
	}

	public ResponseInfo get(){
		return responseInfo;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

}
