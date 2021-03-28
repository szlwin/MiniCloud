package com.minicould.platform.client;

import java.io.IOException;

import com.minicould.service.data.RequestData;
import com.minicould.service.request.RequestInfo;
import com.minicould.service.response.ResponseInfo;
import com.minicould.service.util.Util;
import com.report.parse.ResponseParser;
import com.report.parse.data.ResponseReportData;

public class CloundCmdClient {

	public static void main(String args[]) {
		CloundCmdClient cloundCmdClient = new CloundCmdClient();
		
		try {
			cloundCmdClient.execute(args[0],Integer.valueOf(args[1]),args[2]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ResponseInfo responseInfo  = cloundCmdClient.getResponseInfo();
		
		if(responseInfo.getErrorCode() > 0){
			System.out.println(responseInfo.getErrorCode()+":"+responseInfo.getErrorMsg());
		}else{
			System.out.println("Execute the cmd success");
		}
	}
	
	private ResponseInfo responseInfo;
	
	public void execute(String url,int port,String cmd) throws IOException{
		RequestData requestData = new RequestData();
		requestData.add("cmd", cmd);
		
		RequestInfo requestInfo = Util.parserCmd(new RequestData[]{requestData});
		
		SocketCmdSend socketCmdSend = new SocketCmdSend();
		
		socketCmdSend.send(requestInfo, url, port);
		
		byte[] byteValue = socketCmdSend.get();
		
		parser(byteValue);
	}

	
	public ResponseInfo getResponseInfo() {
		return responseInfo;
	}
	
	private void parser(byte[] byteValue) throws IOException{
		ResponseParser responseParser = new ResponseParser();
		
		ResponseReportData responseReportData = responseParser.parser(byteValue);

		responseInfo = Util.parserResonseData(responseReportData);
	}
	
	/*public void execute(String ip,int port,String cmd) throws IOException{
		Socket socket = new Socket();
		InetSocketAddress socketAddress = new InetSocketAddress(ip,port);
		socket.setKeepAlive(true);
		socket.connect(socketAddress,10000);
		//socket.bind(socketAddress);

		OutputStream outputStream = socket.getOutputStream();
		byte[] cmdByte = cmd.getBytes("utf-8");
		PrintWriter out
		   = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream,"utf-8")));
		System.out.println(cmd);
		out.write(cmd);
		//out.write(cmdByte);
		//out.write("init defaultClound 1.0 -name defaultClound -version 1.0 -type 0 ");
		out.flush();
		socket.shutdownOutput();

		StringBuffer strBuffer = new StringBuffer();
		InputStream inputStream = socket.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		int  res = in.read();
		while(res != -1){
			strBuffer.append((char)res);
			res = in.read();
		}
		System.out.println(strBuffer.toString());
		inputStream.close();
		socket.close();
	}*/
}
