/*
 * Created on Apr 20, 2005
 *
 * ClassName	:  	PayRollIdsDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.payroll;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 */
public class PayRollIdsDetails implements Serializable
{
    Date fromDate;
    Date toDate;
    boolean pyrl_Created;
    boolean pyrl_Closed;
    public PayRollIdsDetails()
    {
        fromDate = null;
        toDate = null;
        pyrl_Created = false;
        pyrl_Closed = false;
    }
    
    /**
     * @return Returns the fromDate.
     */
    public Date getFromDate()
    {
        return fromDate;
    }
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }
    /**
     * @return Returns the pyrl_Closed.
     */
    public boolean isPyrl_Closed()
    {
        return pyrl_Closed;
    }
    /**
     * @param pyrl_Closed The pyrl_Closed to set.
     */
    public void setPyrl_Closed(boolean pyrl_Closed)
    {
        this.pyrl_Closed = pyrl_Closed;
    }
    /**
     * @return Returns the pyrl_Created.
     */
    public boolean isPyrl_Created()
    {
        return pyrl_Created;
    }
    /**
     * @param pyrl_Created The pyrl_Created to set.
     */
    public void setPyrl_Created(boolean pyrl_Created)
    {
        this.pyrl_Created = pyrl_Created;
    }
    /**
     * @return Returns the toDate.
     */
    public Date getToDate()
    {
        return toDate;
    }
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }
}

/*
*$Log: PayRollIdsDetails.java,v $
*Revision 1.1  2005/04/20 12:08:18  kduraisamy
*initial commit.
*
*
*/