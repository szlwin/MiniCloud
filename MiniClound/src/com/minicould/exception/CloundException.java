package com.minicould.exception;

public class CloundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185388348551690841L;

	public CloundException()
	{
		
	}
	
	public CloundException(Exception e)
	{
		super(e);
	}
	
	public CloundException(String msg)
	{
		super(msg);
	}
	
	public CloundException(String msg,Exception e)
	{
		super(msg,e);
	}
}
