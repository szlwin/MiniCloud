package com.minicould.common.data;

public class CmdData extends BaseData{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2442496619530650174L;

	private String cmd;
	
	private CompantData data;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public CompantData getData() {
		return data;
	}

	public void setData(CompantData data) {
		this.data = data;
	}
	
}
