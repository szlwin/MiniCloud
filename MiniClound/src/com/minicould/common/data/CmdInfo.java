package com.minicould.common.data;

public class CmdInfo extends BaseData{

	/**
	 * 
	 */
	private static final long serialVersionUID = -742617958431769004L;
	
	private String name;
	
	private String cname;
	
	private String cversion;
	
	private String arg;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCversion() {
		return cversion;
	}

	public void setCversion(String cversion) {
		this.cversion = cversion;
	}

	public String getArg() {
		return arg;
	}

	public void setArg(String arg) {
		this.arg = arg;
	}

}
