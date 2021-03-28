package com.minicould.common.desc;

import com.minicould.node.SystemCompant;


public class CloundConfigDesc extends SimpleDesc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5486323228853183325L;

	private SystemCompant initCompant;

	private String path;
	
	public SystemCompant getInitCompant() {
		return initCompant;
	}

	public void setInitCompant(SystemCompant initCompant) {
		this.initCompant = initCompant;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
