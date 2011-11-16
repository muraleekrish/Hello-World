/*
 * Created on Jun 3, 2005
 *
 * ClassName	:  	ActualHrsDetails.java
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
 */
public class ActualHrsDetails implements Serializable
{
    private String custName;
    private int custId;
    private String woNo;
    private String jbName;
    private String drwgNo;
    private String revsnNo;
    private String matlType;
    private float stdHrs;
    private float minTimeTaken;
    private float maxTimeTaken;
    private float avgTimeTaken;
    public ActualHrsDetails()
    {
        custName = "";
        custId = 0;
        woNo = "";
        jbName = "";
        drwgNo = "";
        revsnNo = "";
        matlType = "";
        stdHrs = 0;
        minTimeTaken = 0;
        maxTimeTaken = 0;
        avgTimeTaken = 0;
        
    }
    
    
    /**
     * @return Returns the stdHrs.
     */
    public float getStdHrs()
    {
        return stdHrs;
    }
    /**
     * @param avgStdHrs The stdHrs to set.
     */
    public void setStdHrs(float stdHrs)
    {
        this.stdHrs = stdHrs;
    }
    /**
     * @return Returns the avgTimeTaken.
     */
    public float getAvgTimeTaken()
    {
        return avgTimeTaken;
    }
    /**
     * @param avgTimeTaken The avgTimeTaken to set.
     */
    public void setAvgTimeTaken(float avgTimeTaken)
    {
        this.avgTimeTaken = avgTimeTaken;
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
     * @return Returns the jbName.
     */
    public String getJbName()
    {
        return jbName;
    }
    /**
     * @param jbName The jbName to set.
     */
    public void setJbName(String jbName)
    {
        this.jbName = jbName;
    }
    /**
     * @return Returns the matlType.
     */
    public String getMatlType()
    {
        return matlType;
    }
    /**
     * @param matlType The matlType to set.
     */
    public void setMatlType(String matlType)
    {
        this.matlType = matlType;
    }
    /**
     * @return Returns the maxTimeTaken.
     */
    public float getMaxTimeTaken()
    {
        return maxTimeTaken;
    }
    /**
     * @param maxTimeTaken The maxTimeTaken to set.
     */
    public void setMaxTimeTaken(float maxTimeTaken)
    {
        this.maxTimeTaken = maxTimeTaken;
    }
    /**
     * @return Returns the minTimeTaken.
     */
    public float getMinTimeTaken()
    {
        return minTimeTaken;
    }
    /**
     * @param minTimeTaken The minTimeTaken to set.
     */
    public void setMinTimeTaken(float minTimeTaken)
    {
        this.minTimeTaken = minTimeTaken;
    }
    /**
     * @return Returns the revsnNo.
     */
    public String getRevsnNo()
    {
        return revsnNo;
    }
    /**
     * @param revsnNo The revsnNo to set.
     */
    public void setRevsnNo(String revsnNo)
    {
        this.revsnNo = revsnNo;
    }
    /**
     * @return Returns the woNo.
     */
    public String getWoNo()
    {
        return woNo;
    }
    /**
     * @param woNo The woNo to set.
     */
    public void setWoNo(String woNo)
    {
        this.woNo = woNo;
    }
}

/*
*$Log: ActualHrsDetails.java,v $
*Revision 1.1  2005/06/03 07:53:38  kduraisamy
*initial commit.
*
*
*/