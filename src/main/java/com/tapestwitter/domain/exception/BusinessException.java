package com.tapestwitter.domain.exception;

/**
 * Generic business exception
 * 
 * @author karesti
 * 
 */
public class BusinessException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8229837850658777788L;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
