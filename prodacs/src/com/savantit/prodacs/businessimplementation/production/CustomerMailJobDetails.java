/*
 * Created on Dec 21, 2005
 *
 * ClassName	:  	CustomerMailJobDetails.java
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
public class CustomerMailJobDetails implements Serializable
{
    private int jobId;
    private String jobName;
    private String dwgNo;
    private String rvsnNo;
    private String matlType;
    private int balanceForward;
    private int totalQty;
    private int notTakenUp;
    private int workInProcess;
    private int readyToDespatch;
    private int totStatusQty;
   
    Vector vecCustomerOrderDetails;
    Vector vecDespatchDetails;
    
    public CustomerMailJobDetails()
    {
        this.jobId = 0;
        this.jobName = "";
        this.dwgNo = "";
        this.rvsnNo = "";
        this.matlType = "";
        this.vecCustomerOrderDetails = new Vector();
        this.vecDespatchDetails = new Vector();
        this.balanceForward = 0;
        this.totalQty = 0;
        this.notTakenUp = 0;
        this.workInProcess = 0;
        this.readyToDespatch = 0;
        this.totStatusQty = 0;
        
    }
    
    
    
    /**
     * @return Returns the balanceForward.
     */
    public int getBalanceForward()
    {
        return balanceForward;
    }
    /**
     * @param balanceForward The balanceForward to set.
     */
    public void setBalanceForward(int balanceForward)
    {
        this.balanceForward = balanceForward;
    }
    /**
     * @return Returns the dwgNo.
     */
    public String getDwgNo()
    {
        return dwgNo;
    }
    /**
     * @param dwgNo The dwgNo to set.
     */
    public void setDwgNo(String dwgNo)
    {
        this.dwgNo = dwgNo;
    }
    /**
     * @return Returns the jobId.
     */
    public int getJobId()
    {
        return jobId;
    }
    /**
     * @param jobId The jobId to set.
     */
    public void setJobId(int jobId)
    {
        this.jobId = jobId;
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
     * @return Returns the vecCustomerOrderDetails.
     */
    public Vector getVecCustomerOrderDetails()
    {
        return vecCustomerOrderDetails;
    }
    /**
     * @param vecCustomerOrderDetails The vecCustomerOrderDetails to set.
     */
    public void setVecCustomerOrderDetails(Vector vecCustomerOrderDetails)
    {
        this.vecCustomerOrderDetails = vecCustomerOrderDetails;
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
     * @return Returns the rvsnNo.
     */
    public String getRvsnNo()
    {
        return rvsnNo;
    }
    /**
     * @param rvsnNo The rvsnNo to set.
     */
    public void setRvsnNo(String rvsnNo)
    {
        this.rvsnNo = rvsnNo;
    }
    /**
     * @return Returns the totalQty.
     */
    public int getTotalQty()
    {
        return totalQty;
    }
    /**
     * @param totalQty The totalQty to set.
     */
    public void setTotalQty(int totalQty)
    {
        this.totalQty = totalQty;
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
     * @return Returns the totStatusQty.
     */
    public int getTotStatusQty()
    {
        return totStatusQty;
    }
    /**
     * @param totStatusQty The totStatusQty to set.
     */
    public void setTotStatusQty(int totStatusQty)
    {
        this.totStatusQty = totStatusQty;
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
}

/*
*$Log: CustomerMailJobDetails.java,v $
*Revision 1.2  2005/12/21 09:39:48  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*Revision 1.1  2005/12/20 20:55:14  kduraisamy
*signature added for getAlljobStatus for sending mail report to customer.
*
*
*/