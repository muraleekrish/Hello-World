/*
 * Created on Jul 22, 2005
 *
 * ClassName	:  	CustomerViewJobDetails.java
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
public class CustomerViewJobDetails implements Serializable
{
    private int jobId;
    private String jobName;
    private String dwgNo;
    private String rvsnNo;
    private String matlType;
    private JobReceiptDetails objJobReceiptDetails;
    private JobStatusDetails objJobStatusDetails;
    public CustomerViewJobDetails()
    {
        this.jobId = 0;
        this.jobName = "";
        this.dwgNo = "";
        this.rvsnNo = "";
        this.matlType = "";
        this.objJobReceiptDetails = null;
        this.objJobStatusDetails = null;
        
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
     * @return Returns the objJobReceiptDetails.
     */
    public JobReceiptDetails getObjJobReceiptDetails()
    {
        return objJobReceiptDetails;
    }
    /**
     * @param objJobReceiptDetails The objJobReceiptDetails to set.
     */
    public void setObjJobReceiptDetails(JobReceiptDetails objJobReceiptDetails)
    {
        this.objJobReceiptDetails = objJobReceiptDetails;
    }
    /**
     * @return Returns the objJobStatusDetails.
     */
    public JobStatusDetails getObjJobStatusDetails()
    {
        return objJobStatusDetails;
    }
    /**
     * @param objJobStatusDetails The objJobStatusDetails to set.
     */
    public void setObjJobStatusDetails(JobStatusDetails objJobStatusDetails)
    {
        this.objJobStatusDetails = objJobStatusDetails;
    }
}

/*
*$Log: CustomerViewJobDetails.java,v $
*Revision 1.2  2005/07/22 11:38:04  kduraisamy
*jobReceiptDetails vector removed.single object added.
*
*Revision 1.1  2005/07/22 10:45:06  kduraisamy
*initial commit.
*
*
*/