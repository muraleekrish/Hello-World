/*
 * Created on Nov 4, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.employee;

/**
 * @author sduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class EmployeeException extends Exception
{
	private String exceptionMessage = "";
	private String exceptionCode = "";
	private String exceptionGeneral = "";
	public EmployeeException(String code,String msg,String gen)
	{
		this.exceptionMessage = msg;
		this.exceptionCode = code;
		this.exceptionGeneral = gen;
	}
	/*public String toString()
	{
		return "EmployeeException["+this.exceptionMessage+this.exceptionCode+this.exceptionGeneral+"]";
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
$Log: EmployeeException.java,v $
Revision 1.3  2004/11/10 09:08:20  sduraisamy
Arguments modified in constructor

Revision 1.2  2004/11/06 07:39:29  sduraisamy
Employee module modified for Log inclusion and Employee Manager modified for change in Filter Methods

***/