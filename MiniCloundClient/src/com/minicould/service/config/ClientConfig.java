package com.minicould.service.config;

public class ClientConfig {

	private static final ClientConfig clientConfig = new ClientConfig();
	
	//每次业务请求最大数
	private int max = 2;
	
	//每次请求报文最大数
	private int size = 100;
	
	//重试次数(业务)
	private int count = 2;
	
	//更新适配信息时间间隔(ms)
	private int time = 60000;
	
	//异步请求信息保存时间(ms)
	private int dataSaveTime = 600000;

	//适配平台地址
	private String adapterIP;
	
	//适配平台端口
	private String adapterPort;
	
	//重试次数(业务适配请求)
	private int  adapterCount = 1;
	
	//最大并发数
	private int maxCount;
	
	private int version = 1;
	
	private ClientConfig(){
		
	}
	
	public static ClientConfig getInstance(){
		return clientConfig;
		
	}
	
	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getDataSaveTime() {
		return dataSaveTime;
	}

	public void setDataSaveTime(int dataSaveTime) {
		this.dataSaveTime = dataSaveTime;
	}

	public String getAdapterIP() {
		return adapterIP;
	}

	public void setAdapterIP(String adapterIP) {
		this.adapterIP = adapterIP;
	}

	public String getAdapterPort() {
		return adapterPort;
	}

	public void setAdapterPort(String adapterPort) {
		this.adapterPort = adapterPort;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getAdapterCount() {
		return adapterCount;
	}

	public void setAdapterCount(int adapterCount) {
		this.adapterCount = adapterCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	
}
