package com.minicould.common.desc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Desc implements Cloneable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5993678679631059738L;

	protected String name;
	
	protected String version;
	
	protected List<Desc> childDescList  = new ArrayList<Desc>(15);
	
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
	
	public List<Desc> getChildDesc(){
		return childDescList;
	}
}
