/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	JobStatusDetails.java
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
import java.util.Vector;

/**
 * @author kduraisamy
 *
 */
public class JobStatusDetails implements Serializable
{
    private int notTakenUp;
    private int workInProcess;
    private int readyToDespatch;
    private Vector vecDespatchDetails;
    private int totQty;
    public JobStatusDetails()
    {
        this.notTakenUp = 0;
        this.vecDespatchDetails = new Vector();
        this.workInProcess = 0;
        this.readyToDespatch = 0;
        this.totQty = 0;
    }
        

    /**
     * @return Returns the readyToDespatch.
     */
    public int getReadyToDespatch()
    {
        return readyToDespatch;
    }
    /**
     * @param readyToDespatch The readyToDespatch to set.
     */
    public void setReadyToDespatch(int readyToDespatch)
    {
        this.readyToDespatch = readyToDespatch;
    }
    /**
     * @return Returns the totQty.
     */
    public int getTotQty()
    {
        return totQty;
    }
    /**
     * @param totQty The totQty to set.
     */
    public void setTotQty(int totQty)
    {
        this.totQty = totQty;
    }
    /**
     * @return Returns the vecDespatchDetails.
     */
    public Vector getVecDespatchDetails()
    {
        return vecDespatchDetails;
    }
    /**
     * @param vecDespatchDetails The vecDespatchDetails to set.
     */
    public void setVecDespatchDetails(Vector vecDespatchDetails)
    {
        this.vecDespatchDetails = vecDespatchDetails;
    }
    /**
     * @return Returns the workInProcess.
     */
    public int getWorkInProcess()
    {
        return workInProcess;
    }
    /**
     * @param workInProcess The workInProcess to set.
     */
    public void setWorkInProcess(int workInProcess)
    {
        this.workInProcess = workInProcess;
    }
    /**
     * @return Returns the notTakenUp.
     */
    public int getNotTakenUp()
    {
        return notTakenUp;
    }
    /**
     * @param notTakenUp The notTakenUp to set.
     */
    public void setNotTakenUp(int notTakenUp)
    {
        this.notTakenUp = notTakenUp;
    }
}

/*
*$Log: JobStatusDetails.java,v $
*Revision 1.2  2005/07/25 06:55:03  kduraisamy
*field added for NotTakenUp.
*
*Revision 1.1  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*
*/