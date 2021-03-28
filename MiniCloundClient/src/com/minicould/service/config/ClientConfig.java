package com.minicould.service.config;

public class ClientConfig {

	private static final ClientConfig clientConfig = new ClientConfig();
	
	//ÿ��ҵ�����������
	private int max = 2;
	
	//ÿ�������������
	private int size = 100;
	
	//���Դ���(ҵ��)
	private int count = 2;
	
	//����������Ϣʱ����(ms)
	private int time = 60000;
	
	//�첽������Ϣ����ʱ��(ms)
	private int dataSaveTime = 600000;

	//����ƽ̨��ַ
	private String adapterIP;
	
	//����ƽ̨�˿�
	private String adapterPort;
	
	//���Դ���(ҵ����������)
	private int  adapterCount = 1;
	
	//��󲢷���
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
