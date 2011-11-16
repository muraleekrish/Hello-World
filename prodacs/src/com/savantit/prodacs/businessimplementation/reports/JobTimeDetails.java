/*
 * Created on Oct 11, 2005
 *
 * ClassName	:  	JobTimeDetails.java
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
public class JobTimeDetails implements Serializable
{
    private String jbName;
    private String drwgNo;
    private float minTimeTaken;
    private float maxTimeTaken;
    private float avgTimeTaken;
    private int jbTotQty;
    private int jbFinishedQty;
    public JobTimeDetails()
    {
        this.jbName = "";
        this.drwgNo = "";
        this.minTimeTaken = 0;
        this.maxTimeTaken = 0;
        this.avgTimeTaken = 0;
        this.jbTotQty = 0;
        this.jbFinishedQty = 0;
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
     * @return Returns the jbFinishedQty.
     */
    public int getJbFinishedQty()
    {
        return jbFinishedQty;
    }
    /**
     * @param jbFinishedQty The jbFinishedQty to set.
     */
    public void setJbFinishedQty(int jbFinishedQty)
    {
        this.jbFinishedQty = jbFinishedQty;
    }
    /**
     * @return Returns the jbTotQty.
     */
    public int getJbTotQty()
    {
        return jbTotQty;
    }
    /**
     * @param jbTotQty The jbTotQty to set.
     */
    public void setJbTotQty(int jbTotQty)
    {
        this.jbTotQty = jbTotQty;
    }
}

/*
*$Log: JobTimeDetails.java,v $
*Revision 1.1  2005/10/17 09:02:07  kduraisamy
*initial commit.
*
*
*/