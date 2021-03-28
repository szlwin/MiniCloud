package com.minicould.platform.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.minicould.service.request.RequestInfo;

public class SocketCmdSend {
	
	private byte[] byteArray;
	
	public void send(RequestInfo requestInfo,String url,int port) throws IOException{
		
		byte[] byteValue = (byte[]) requestInfo.getValue();
		
		Socket socket = new Socket();
		
		InetSocketAddress socketAddress = new InetSocketAddress(url,port);
		
		socket.setKeepAlive(true);
		
		socket.connect(socketAddress,requestInfo.getServiceAdapterInfo().getTimeOut());

		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(byteValue);
		
		outputStream.flush();
		socket.shutdownOutput();
		
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
	}
	

	public byte[] get(){
		return byteArray;
	}
}
