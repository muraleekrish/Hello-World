/*
 * Created on Jul 16, 2005
 *
 * ClassName	:  	WorkOrderJobStatusDetails.java
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
public class WorkOrderJobStatusDetails implements Serializable
{
    private int woId;
    private String workOrderNo;
    private Date woDate;
    private int jobId;
	private String jobName;
	private String jobDrwgNo;
	private String jobRvsnNo;
	private String jobMatlType;
	private int jobQtySno;
	private int woJbStatId;
	private boolean orderTaken;
	private boolean workInProcess;
	private boolean despatchClearance;
	private boolean shipment;
	
	public WorkOrderJobStatusDetails()
	{
	    woId = 0;
	    workOrderNo = "";
	    woDate = null;
	    jobId = 0;
	    jobName = "";
	    jobDrwgNo = "";
	    jobRvsnNo  = "";
	    jobMatlType = "";
	    jobQtySno = 0;
	    woJbStatId = 0;
	    orderTaken = false;
	    workInProcess = false;
	    despatchClearance = false;
	    shipment = false;
	}

    /**
     * @return Returns the despatchClearance.
     */
    public boolean isDespatchClearance()
    {
        return despatchClearance;
    }
    /**
     * @param despatchClearance The despatchClearance to set.
     */
    public void setDespatchClearance(boolean despatchClearance)
    {
        this.despatchClearance = despatchClearance;
    }
    /**
     * @return Returns the jobDrwgNo.
     */
    public String getJobDrwgNo()
    {
        return jobDrwgNo;
    }
    /**
     * @param jobDrwgNo The jobDrwgNo to set.
     */
    public void setJobDrwgNo(String jobDrwgNo)
    {
        this.jobDrwgNo = jobDrwgNo;
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
     * @return Returns the jobMatlType.
     */
    public String getJobMatlType()
    {
        return jobMatlType;
    }
    /**
     * @param jobMatlType The jobMatlType to set.
     */
    public void setJobMatlType(String jobMatlType)
    {
        this.jobMatlType = jobMatlType;
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
     * @return Returns the jobQtySno.
     */
    public int getJobQtySno()
    {
        return jobQtySno;
    }
    /**
     * @param jobQtySno The jobQtySno to set.
     */
    public void setJobQtySno(int jobQtySno)
    {
        this.jobQtySno = jobQtySno;
    }
    /**
     * @return Returns the jobRvsnNo.
     */
    public String getJobRvsnNo()
    {
        return jobRvsnNo;
    }
    /**
     * @param jobRvsnNo The jobRvsnNo to set.
     */
    public void setJobRvsnNo(String jobRvsnNo)
    {
        this.jobRvsnNo = jobRvsnNo;
    }
    /**
     * @return Returns the orderTaken.
     */
    public boolean isOrderTaken()
    {
        return orderTaken;
    }
    /**
     * @param orderTaken The orderTaken to set.
     */
    public void setOrderTaken(boolean orderTaken)
    {
        this.orderTaken = orderTaken;
    }
    /**
     * @return Returns the shipment.
     */
    public boolean isShipment()
    {
        return shipment;
    }
    /**
     * @param shipment The shipment to set.
     */
    public void setShipment(boolean shipment)
    {
        this.shipment = shipment;
    }
    /**
     * @return Returns the woId.
     */
    public int getWoId()
    {
        return woId;
    }
    /**
     * @param woId The woId to set.
     */
    public void setWoId(int woId)
    {
        this.woId = woId;
    }
    /**
     * @return Returns the workInProcess.
     */
    public boolean isWorkInProcess()
    {
        return workInProcess;
    }
    /**
     * @param workInProcess The workInProcess to set.
     */
    public void setWorkInProcess(boolean workInProcess)
    {
        this.workInProcess = workInProcess;
    }
    /**
     * @return Returns the workOrderNo.
     */
    public String getWorkOrderNo()
    {
        return workOrderNo;
    }
    /**
     * @param workOrderNo The workOrderNo to set.
     */
    public void setWorkOrderNo(String workOrderNo)
    {
        this.workOrderNo = workOrderNo;
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
    /**
     * @return Returns the woJbStatId.
     */
    public int getWoJbStatId()
    {
        return woJbStatId;
    }
    /**
     * @param woJbStatId The woJbStatId to set.
     */
    public void setWoJbStatId(int woJbStatId)
    {
        this.woJbStatId = woJbStatId;
    }
}

/*
*$Log: WorkOrderJobStatusDetails.java,v $
*Revision 1.2  2005/07/18 09:06:22  kduraisamy
*field woJbstatId added in value object.
*
*Revision 1.1  2005/07/16 10:28:47  kduraisamy
*work Orderjobstatus filter method query added.
*
*
*/