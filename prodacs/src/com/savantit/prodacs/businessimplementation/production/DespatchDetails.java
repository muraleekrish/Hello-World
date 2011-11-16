/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	DespatchDetails.java
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
public class DespatchDetails implements Serializable
{
    private Date despatchDate;
    private String companyDCNo;
    private int jbQty;
    public DespatchDetails()
    {
        this.despatchDate = null;
        this.companyDCNo = "";
        this.jbQty = 0;
    }

    /**
     * @return Returns the companyDCNo.
     */
    public String getCompanyDCNo()
    {
        return companyDCNo;
    }
    /**
     * @param companyDCNo The companyDCNo to set.
     */
    public void setCompanyDCNo(String companyDCNo)
    {
        this.companyDCNo = companyDCNo;
    }
    /**
     * @return Returns the despatchDate.
     */
    public Date getDespatchDate()
    {
        return despatchDate;
    }
    /**
     * @param despatchDate The despatchDate to set.
     */
    public void setDespatchDate(Date despatchDate)
    {
        this.despatchDate = despatchDate;
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
}

/*
*$Log: DespatchDetails.java,v $
*Revision 1.1  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*
*/