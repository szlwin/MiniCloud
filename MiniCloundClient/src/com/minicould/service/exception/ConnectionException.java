package com.minicould.service.exception;

public class ConnectionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionException()
	{
		
	}
	
	public ConnectionException(Exception e)
	{
		super(e);
	}
	
	public ConnectionException(String msg)
	{
		super(msg);
	}

}
