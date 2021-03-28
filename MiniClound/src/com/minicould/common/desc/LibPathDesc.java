package com.minicould.common.desc;

public class LibPathDesc extends Desc{

	public final static String SYSTEM_LIB = "systemLib";
	
	public final static String COMPANT_LIB = "compantLib";
	
	public final static String MOUDLE_LIB = "moudleLib";
	
	public final static String SERVICE_LIB = "serviceLib";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199362731491215337L;
	
	
	private String libPath;


	public String getLibPath() {
		return libPath;
	}


	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}
	
	
}
