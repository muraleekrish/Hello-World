/*
 * Created on Apr 20, 2005
 *
 * ClassName	:  	CustomNonAvbltyDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.workcalendar;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 */
public class CustomNonAvbltyDetails implements Serializable
{
    Date forDate;
	String nonAvbltyReason;
	public CustomNonAvbltyDetails()
	{
	    forDate = null;
	    nonAvbltyReason = "";
	}
	
    /**
     * @return Returns the forDate.
     */
    public Date getForDate()
    {
        return forDate;
    }
    /**
     * @param forDate The forDate to set.
     */
    public void setForDate(Date forDate)
    {
        this.forDate = forDate;
    }
    /**
     * @return Returns the nonAvbltyReason.
     */
    public String getNonAvbltyReason()
    {
        return nonAvbltyReason;
    }
    /**
     * @param nonAvbltyReason The nonAvbltyReason to set.
     */
    public void setNonAvbltyReason(String nonAvbltyReason)
    {
        this.nonAvbltyReason = nonAvbltyReason;
    }
}

/*
*$Log: CustomNonAvbltyDetails.java,v $
*Revision 1.1  2005/04/20 10:10:10  kduraisamy
*avbltyDetails() add method changed.
*
*
*/