package com.minicould.service.port;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.minicould.service.request.RequestInfo;

public class SocketSend {
	
	private byte[] byteArray;
	
	public void send(RequestInfo requestInfo) throws IOException{
		
		byte[] byteValue = (byte[]) requestInfo.getValue();
		String url = requestInfo.getServiceAdapterInfo().getUrl();
		
		int port = requestInfo.getServiceAdapterInfo().getPort();
		
		//int type = requestInfo.getServiceAdapterInfo().getServiceRequestData().getType();
		
		boolean isNeedResponse = requestInfo.getServiceAdapterInfo().getServiceRequestData().isNeedResponse();
		
		Socket socket = new Socket();
		
		InetSocketAddress socketAddress = new InetSocketAddress(url,port);
		
		socket.setKeepAlive(true);
		
		socket.connect(socketAddress,requestInfo.getServiceAdapterInfo().getTimeOut());

		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(byteValue);
		
		outputStream.flush();
		socket.shutdownOutput();

		if(!isNeedResponse){
			socket.close();
			return;
		}
		
		StringBuffer strBuffer = new StringBuffer();
		InputStream inputStream = socket.getInputStream();
		
		int  res = inputStream.read();
		while(res != -1){
			strBuffer.append((byte)res);
			res = inputStream.read();
		}
		inputStream.close();
		socket.close();
		
		byteArray = strBuffer.toString().getBytes();
		//parse(byteValue);
	}
	

	public byte[] get(){
		return byteArray;
	}
}
