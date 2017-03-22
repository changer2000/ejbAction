 /*
  * System-Name    :NCU (NITTSU COMMON UTILITY)
  * Subsystem-Name :COM
  *
  * Notes          :
  *
  * Copyright:
  * All Rights Reserved Copyright (C) NIPPON EXPRESS  2015
  *
  */
package home.study.system;


/**
 * Exception class
 * <pre>
 * [Revision History]
 * 1.0.0.0 2015/11/27 New
 * </pre>
 * @version 1.0.0.0 2015/11/27
 * @author  CITS
 * @see  
 */
public class HsSystemException extends RuntimeException {

	private static final String MESSAGE="NCU System Error";
	private HsLocaleMessage localeMessage=null;
	
	public HsSystemException() {
		super(MESSAGE);
	}
	
	public HsSystemException(Throwable cause) {
		super(cause);
	}
	
	public HsSystemException(String messageKey, String[] params) {
		this();
		this.localeMessage=new HsLocaleMessage(messageKey, params);
	}
	
	public HsSystemException(String messageKey, String[] params, Throwable cause) {
		this(cause);
		this.localeMessage=new HsLocaleMessage(messageKey, params);
	}
	
	public HsSystemException(HsLocaleMessage localeMessage) {
		this();
		this.localeMessage=localeMessage;
	}
	
	public HsSystemException(HsLocaleMessage localeMessage, Throwable cause) {
		this(cause);
		this.localeMessage=localeMessage;
	}
	
	public String getMessageKey() {
		return (localeMessage!=null?localeMessage.getMessageKey():null);
	}
	
	public String[] getParams() {
		return (localeMessage!=null?localeMessage.getParams():null);
	}
	
	public HsLocaleMessage getLocaleMessage() {
		return localeMessage;
	}
	
	public void setLocaleMessage(String messageKey, String[] params) {
		this.localeMessage=new HsLocaleMessage(messageKey, params);
	}

	public void setLocaleMessage(HsLocaleMessage localeMessage) {
		this.localeMessage = localeMessage;
	}
}
