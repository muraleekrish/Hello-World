/*
 * Created on Feb 17, 2005
 *
 * ClassName	:  	TopSheetDetails.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.businessimplementation.reports;

import java.io.Serializable;

/**
 * @author kduraisamy
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TopSheetDetails implements Serializable
{
    private int custId;
    private String custName;
    private String jobName;
    private String drwgNo;
    private float vtlHrs;
    private float hbHrs;
    private float drlHrs;
    public TopSheetDetails()
    {
        custName = "";
        jobName = "";
        drwgNo = "";
        vtlHrs = 0;
        hbHrs = 0;
        drlHrs = 0;
        custId = 0;
    }
    
    /**
     * @return Returns the custId.
     */
    public int getCustId()
    {
        return custId;
    }
    /**
     * @param custId The custId to set.
     */
    public void setCustId(int custId)
    {
        this.custId = custId;
    }
    /**
     * @return Returns the custName.
     */
    public String getCustName()
    {
        return custName;
    }
    /**
     * @param custName The custName to set.
     */
    public void setCustName(String custName)
    {
        this.custName = custName;
    }
    /**
     * @return Returns the drlHrs.
     */
    public float getDrlHrs()
    {
        return drlHrs;
    }
    /**
     * @param drlHrs The drlHrs to set.
     */
    public void setDrlHrs(float drlHrs)
    {
        this.drlHrs = drlHrs;
    }
    /**
     * @return Returns the drwgNo.
     */
    public String getDrwgNo()
    {
        return drwgNo;
    }
    /**
     * @param drwgNo The drwgNo to set.
     */
    public void setDrwgNo(String drwgNo)
    {
        this.drwgNo = drwgNo;
    }
    /**
     * @return Returns the hbHrs.
     */
    public float getHbHrs()
    {
        return hbHrs;
    }
    /**
     * @param hbHrs The hbHrs to set.
     */
    public void setHbHrs(float hbHrs)
    {
        this.hbHrs = hbHrs;
    }
    /**
     * @return Returns the jobName.
     */
    public String getJobName()
    {
        return jobName;
    }
    /**
     * @param jobName The jobName to set.
     */
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }
    /**
     * @return Returns the vtlHrs.
     */
    public float getVtlHrs()
    {
        return vtlHrs;
    }
    /**
     * @param vtlHrs The vtlHrs to set.
     */
    public void setVtlHrs(float vtlHrs)
    {
        this.vtlHrs = vtlHrs;
    }
}

/*
*$Log: TopSheetDetails.java,v $
*Revision 1.2  2005/02/22 06:13:47  kduraisamy
*initial commit.
*
*Revision 1.1  2005/02/18 06:35:09  kduraisamy
*initial commit.
*
*
*/