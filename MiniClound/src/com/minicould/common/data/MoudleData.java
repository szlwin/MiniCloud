package com.minicould.common.data;

public class MoudleData extends CloundData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String version;
	
	private int type;
	
	private String cmd;

	private CloundData data;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public CloundData getData() {
		return data;
	}

	public void setData(CloundData data) {
		this.data = data;
	}
	
}
