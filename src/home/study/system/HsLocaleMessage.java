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
 * Business DTO class
 * <pre>
 * [Revision History]
 * 1.0.0.0 2015/11/27 New
 * </pre>
 * @version 1.0.0.0 2015/11/27
 * @author  CITS
 * @see  
 */
public class HsLocaleMessage {

	private String messageKey;
	private String[] params;
	
	public HsLocaleMessage() {
	}
	
	public HsLocaleMessage(String messageKey, String[] params) {
		this.messageKey=messageKey;
		this.params=params;
	}
	
	public String getMessageKey() {
		return messageKey;
	}
	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}
	public String[] getParams() {
		return params;
	}
	public void setParams(String[] params) {
		this.params = params;
	}
}
