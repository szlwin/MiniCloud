package com.minicould.service.data;

public class ServiceAdapterInfo {

	private int requetsType;
	
	private String url;
	
	private int port;
	
	private int timeOut;
	
	private int reportVersion;
	
	private ServiceRequestInfo serviceRequestData;

	public int getRequetsType() {
		return requetsType;
	}

	public void setRequetsType(int requetsType) {
		this.requetsType = requetsType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ServiceRequestInfo getServiceRequestData() {
		return serviceRequestData;
	}

	public void setServiceRequestData(ServiceRequestInfo serviceRequestData) {
		this.serviceRequestData = serviceRequestData;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public int getReportVersion() {
		return reportVersion;
	}

	public void setReportVersion(int reportVersion) {
		this.reportVersion = reportVersion;
	}
	
	
}
