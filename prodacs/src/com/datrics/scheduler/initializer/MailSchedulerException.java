/*
 * Created on Jan 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.datrics.scheduler.initializer;

/**
 * @author vkrishnamoorthy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MailSchedulerException extends Exception 
{
	private String exceptionMessage;
	private String exceptionCode;
	private String exceptionGeneral;
	MailSchedulerException(String exceptionCode,String exceptionMssage,String exceptionGeneral)
	{
		this.exceptionMessage = exceptionMssage;
		this.exceptionCode = exceptionCode;
		this.exceptionGeneral=exceptionGeneral;
	}
	
	/**
	 * @return Returns the exceptionCode.
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
	 * @return Returns the exceptionGeneral.
	 */
	public String getExceptionGeneral() {
		return exceptionGeneral;
	}
	/**
	 * @param exceptionGeneral The exceptionGeneral to set.
	 */
	public void setExceptionGeneral(String exceptionGeneral) {
		this.exceptionGeneral = exceptionGeneral;
	}
	/**
	 * @return Returns the exceptionMessage.
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	/**
	 * @param exceptionMessage The exceptionMessage to set.
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}


/***
$Log: MailSchedulerException.java,v $
Revision 1.1  2006/01/07 05:55:09  vkrishnamoorthy
Initial commit on ProDACS.

***/