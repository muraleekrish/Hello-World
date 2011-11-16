/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	CustomerOrderDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.production;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 */
public class CustomerOrderDetails implements Serializable
{
    private Date woDate;
    private String custDCNo;
    private int jbQty;
    public CustomerOrderDetails()
    {
        this.woDate = null;
        this.custDCNo = "";
        this.jbQty = 0;
    }
    
    
    /**
     * @return Returns the custDCNo.
     */
    public String getCustDCNo()
    {
        return custDCNo;
    }
    /**
     * @param custDCNo The custDCNo to set.
     */
    public void setCustDCNo(String custDCNo)
    {
        this.custDCNo = custDCNo;
    }
    /**
     * @return Returns the jbQty.
     */
    public int getJbQty()
    {
        return jbQty;
    }
    /**
     * @param jbQty The jbQty to set.
     */
    public void setJbQty(int jbQty)
    {
        this.jbQty = jbQty;
    }
    /**
     * @return Returns the woDate.
     */
    public Date getWoDate()
    {
        return woDate;
    }
    /**
     * @param woDate The woDate to set.
     */
    public void setWoDate(Date woDate)
    {
        this.woDate = woDate;
    }
}

/*
*$Log: CustomerOrderDetails.java,v $
*Revision 1.1  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*
*/