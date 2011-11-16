/*
 * Created on Oct 29, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.customer;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class CustomerException extends Exception  
{
	private String exceptionMessage;
	private String exceptionCode;
	private String exceptionGeneral;
	CustomerException(String exceptionCode,String exceptionMssage,String exceptionGeneral)
	{
		this.exceptionMessage = exceptionMssage;
		this.exceptionCode = exceptionCode;
		this.exceptionGeneral=exceptionGeneral;
	}

	/*public String toString()
	{
		return "Customer Exception["+this .exceptionCode+":"+this.exceptionMessage+"]"+this.exceptionGeneral;
	}*/
	

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
$Log: CustomerException.java,v $
Revision 1.2  2004/11/09 04:52:36  kduraisamy
Log added

***/