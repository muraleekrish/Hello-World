/*
 * Created on Mar 3, 2005
 *
 * ClassName	:  	InvalidDriverException.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.infra.util.exception;

/**
 * @author kduraisamy
 *
 */
public class InvalidDriverException extends Exception
{
    private String message = "";
	private String errCode = "";

	public InvalidDriverException()
	{
		super();
	}

	public InvalidDriverException(String errCode, String errMessage)
	{
		super();
		this.errCode = errCode;
		this.message = errMessage;
	}

	public InvalidDriverException(String errCode, String errMessage, Throwable rootCause)
	{
		super();
		this.errCode = errCode;
		this.message = errMessage;
		this.initCause(rootCause);
	}

	/**
	 * @return Returns the errCode.
	 */
	public String getErrCode() {
		return errCode;
	}
	/**
	 * @return Returns the errMessage.
	 */
	public String getMessage() {
		return message;
	}

}

/*
*$Log: InvalidDriverException.java,v $
*Revision 1.1  2005/03/04 09:13:33  kduraisamy
*initial commit.
*
*
*/