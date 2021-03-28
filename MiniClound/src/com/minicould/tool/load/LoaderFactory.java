package com.minicould.tool.load;

public class LoaderFactory {

	private static final DycClassLoader dycClassLoader = new DycClassLoader();
	
	public static synchronized DycClassLoader getDefaultClassLoader(){
		return dycClassLoader;
	}
}
