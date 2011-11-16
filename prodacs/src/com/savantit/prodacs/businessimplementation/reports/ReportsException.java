/*
 * Created on Feb 10, 2005
 *
 * ClassName	:  	ReportsException.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reports;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ReportsException extends Exception
{
    
      	private String exceptionMessage = "";
    	private String exceptionCode = "";
    	private String exceptionGeneral = "";
    	public ReportsException(String code,String msg,String gen)
    	{
    		this.exceptionMessage = msg;
    		this.exceptionCode = code;
    		this.exceptionGeneral = gen;
    	}
    	
    	
        /**
         * @return Returns the exceptionCode.
         */
        public String getExceptionCode()
        {
            return exceptionCode;
        }
        /**
         * @param exceptionCode The exceptionCode to set.
         */
        public void setExceptionCode(String exceptionCode)
        {
            this.exceptionCode = exceptionCode;
        }
        /**
         * @return Returns the exceptionGeneral.
         */
        public String getExceptionGeneral()
        {
            return exceptionGeneral;
        }
        /**
         * @param exceptionGeneral The exceptionGeneral to set.
         */
        public void setExceptionGeneral(String exceptionGeneral)
        {
            this.exceptionGeneral = exceptionGeneral;
        }
        /**
         * @return Returns the exceptionMessage.
         */
        public String getExceptionMessage()
        {
            return exceptionMessage;
        }
        /**
         * @param exceptionMessage The exceptionMessage to set.
         */
        public void setExceptionMessage(String exceptionMessage)
        {
            this.exceptionMessage = exceptionMessage;
        }
}

/*
*$Log: ReportsException.java,v $
*Revision 1.1  2005/02/11 06:55:40  kduraisamy
*initial commit.
*
*
*/