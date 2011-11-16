/*
 * Created on Dec 2, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WOJobDetails implements Serializable 
{
	private int jobSerialNo;
	private int generalId;
	private String generalName;
	private int jobId;
	private String jobName;
	private String jobDrwgNo;
	private String jobRvsnNo;
	private String jobMatlType;
	private String jobStatus;
	private int jobQtyStartSno;
	private int jobQty;
	private String woDCNo;
	private Date woDCDate;
	private String woJbStatus;
	private boolean prodEntered;
	private Vector WOJobOpnDetails;
	
	public WOJobDetails()
	{
		this.jobSerialNo = 0; 
		this.generalId = 0;
		this.generalName = "";
		this.jobId = 0;
		this.jobName = "";
		this.jobDrwgNo = "";
		this.jobRvsnNo = "";
		this.jobMatlType = "";
		this.jobStatus = "";
		this.jobQtyStartSno = 0;
		this.jobQty = 0;
		this.woDCNo = "";
		this.woDCDate = null;
		this.woJbStatus = "";
		this.prodEntered = false;
	}
		
	/**
	 * @return Returns the generalId.
	 */
	public int getGeneralId() {
		return generalId;
	}
	/**
	 * @param generalId The generalId to set.
	 */
	public void setGeneralId(int generalId) {
		this.generalId = generalId;
	}
	/**
	 * @return Returns the generalName.
	 */
	public String getGeneralName() {
		return generalName;
	}
	/**
	 * @param generalName The generalName to set.
	 */
	public void setGeneralName(String generalName) {
		this.generalName = generalName;
	}
	/**
	 * @return Returns the jobDrwgNo.
	 */
	public String getJobDrwgNo() {
		return jobDrwgNo;
	}
	/**
	 * @param jobDrwgNo The jobDrwgNo to set.
	 */
	public void setJobDrwgNo(String jobDrwgNo) {
		this.jobDrwgNo = jobDrwgNo;
	}
	/**
	 * @return Returns the jobId.
	 */
	public int getJobId() {
		return jobId;
	}
	/**
	 * @param jobId The jobId to set.
	 */
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	/**
	 * @return Returns the jobMatlType.
	 */
	public String getJobMatlType() {
		return jobMatlType;
	}
	/**
	 * @param jobMatlType The jobMatlType to set.
	 */
	public void setJobMatlType(String jobMatlType) {
		this.jobMatlType = jobMatlType;
	}
	/**
	 * @return Returns the jobName.
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName The jobName to set.
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return Returns the jobRvsnNo.
	 */
	public String getJobRvsnNo() {
		return jobRvsnNo;
	}
	/**
	 * @param jobRvsnNo The jobRvsnNo to set.
	 */
	public void setJobRvsnNo(String jobRvsnNo) {
		this.jobRvsnNo = jobRvsnNo;
	}
	/**
	 * @return Returns the jobSerialNo.
	 */
	public int getJobSerialNo() {
		return jobSerialNo;
	}
	/**
	 * @param jobSerialNo The jobSerialNo to set.
	 */
	public void setJobSerialNo(int jobSerialNo) {
		this.jobSerialNo = jobSerialNo;
	}
	/**
	 * @return Returns the jobQty.
	 */
	public int getJobQty() {
		return jobQty;
	}
	/**
	 * @param jobQty The jobQty to set.
	 */
	public void setJobQty(int jobQty) {
		this.jobQty = jobQty;
	}
	/**
	 * @return Returns the jobQtyStartSno.
	 */
	public int getJobQtyStartSno() {
		return jobQtyStartSno;
	}
	/**
	 * @param jobQtyStartSno The jobQtyStartSno to set.
	 */
	public void setJobQtyStartSno(int jobQtyStartSno) {
		this.jobQtyStartSno = jobQtyStartSno;
	}
	/**
	 * @return Returns the wOJobOpnDetails.
	 */
	public Vector getWOJobOpnDetails() {
		return WOJobOpnDetails;
	}
	/**
	 * @param jobOpnDetails The wOJobOpnDetails to set.
	 */
	public void setWOJobOpnDetails(Vector jobOpnDetails) {
		WOJobOpnDetails = jobOpnDetails;
	}
	/**
	 * @return Returns the jobStatus.
	 */
	public String getJobStatus() {
		return jobStatus;
	}
	/**
	 * @param jobStatus The jobStatus to set.
	 */
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
    /**
     * @return Returns the woDCNo.
     */
    public String getWoDCNo()
    {
        return woDCNo;
    }
    /**
     * @param woDCNo The woDCNo to set.
     */
    public void setWoDCNo(String woDCNo)
    {
        this.woDCNo = woDCNo;
    }
    /**
     * @return Returns the woDCDate.
     */
    public Date getWoDCDate()
    {
        return woDCDate;
    }
    /**
     * @param woDCDate The woDCDate to set.
     */
    public void setWoDCDate(Date woDCDate)
    {
        this.woDCDate = woDCDate;
    }
    /**
     * @return Returns the prodEntered.
     */
    public boolean isProdEntered()
    {
        return prodEntered;
    }
    /**
     * @param prodEntered The prodEntered to set.
     */
    public void setProdEntered(boolean prodEntered)
    {
        this.prodEntered = prodEntered;
    }
    /**
     * @return Returns the woJbStatus.
     */
    public String getWoJbStatus()
    {
        return woJbStatus;
    }
    /**
     * @param woJbStatus The woJbStatus to set.
     */
    public void setWoJbStatus(String woJbStatus)
    {
        this.woJbStatus = woJbStatus;
    }
}
/***
$Log: WOJobDetails.java,v $
Revision 1.8  2005/08/09 04:46:52  kduraisamy
statuses added in wo view.

Revision 1.7  2005/08/01 04:35:40  kduraisamy
wo order modification changed.

Revision 1.6  2005/07/25 06:36:49  kduraisamy
WOJB DCDATE ADDED.

Revision 1.5  2005/07/23 11:15:11  kduraisamy
CUSTOMER WORK ORDER DC NO ADDED.

Revision 1.4  2004/12/09 05:54:46  kduraisamy
Log added.

***/
