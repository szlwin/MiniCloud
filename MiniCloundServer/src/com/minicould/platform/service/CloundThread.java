package com.minicould.platform.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.minicould.common.result.CompantResult;
import com.minicould.common.util.CloundUtil;

public class CloundThread extends Thread{

	private static final Object lock = new Object();
	
	private String cmd;
	
	private Socket socket;
	
	public CloundThread(Socket socket){
		this.socket = socket;
		init();
	}
	
	private void init(){
		this.setContextClassLoader(null);
	}
	
	public void run(){
		
		try {
			
			cmd = getCmd();
			System.out.println(cmd);
			CompantResult compantResult = null;
			
			if(cmd.startsWith("service")){
				compantResult = CloundUtil.execCmd(cmd);
			}else{
				synchronized(lock){
					compantResult = CloundUtil.execCmd(cmd);
				}
			}
			
			if(compantResult.isSuccess()){
				doSuccess(compantResult.getValue());
			}else{
				doFail(compantResult.getErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				
				doFail("exec " + cmd +" fail!");
			} catch (IOException e1) {
				e.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				close();
			} catch (IOException e) {
			}
		}
		
	}
	
	private String getCmd() throws IOException{
		StringBuffer strBuffer = new StringBuffer();
		InputStream inputStream = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		int  res = in.read();
		while(res != -1){
			strBuffer.append((char)res);
			res = in.read();
		}
		//inputStream.read(b)
		socket.shutdownInput();
		return strBuffer.toString();
	}
	
	private void doSuccess(Object object) throws IOException{
		OutputStream outputStream;

		outputStream = socket.getOutputStream();
		PrintWriter out
		   = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8")));
		out.println("execute the cmd success!");
		
		if(object != null && object instanceof String)
			out.println(object);
		out.flush();
		out.close();
	}
	
	private void doFail(Object object) throws IOException{
		OutputStream outputStream;

		outputStream = socket.getOutputStream();
		PrintWriter out
		   = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
		out.println("execute the cmd fail!");

		if(object != null && object instanceof String)
			out.println(object);
		out.flush();
		out.close();
	}
	
	private void close() throws IOException{
		socket.close();
	}
}
