package com.minicould.exception;

public class ConfigException extends CloundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8435181366371533410L;

	
	public ConfigException()
	{
		
	}
	
	public ConfigException(Exception e)
	{
		super(e);
	}
	
	public ConfigException(String msg)
	{
		super(msg);
	}
	
	public ConfigException(String msg,Exception e)
	{
		super(msg,e);
	}
	
}
