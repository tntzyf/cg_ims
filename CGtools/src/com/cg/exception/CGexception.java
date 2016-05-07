package com.cg.exception;

public class CGexception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CGexception(Exception e){
		super(e);
	}
	
	public CGexception(String string){
		super(string);
	}
	
	public CGexception(){
		super();
	}
	
	public CGexception(String message, Throwable cause){
		super(message, cause);
	}
}
