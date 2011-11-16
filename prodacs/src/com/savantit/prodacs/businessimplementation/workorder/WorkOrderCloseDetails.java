/*
 * Created on Dec 7, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.businessimplementation.workorder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kduraisamy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkOrderCloseDetails implements Serializable
{
	private String workOrderNo;
	private Date workOrderDate;
	private int workOrderId;
	private String custName;
	private String jobName;
	private String drwgNo;
	private String rvsnNo;
	private String matlType;
	private int workOrderJbId;
	
	public WorkOrderCloseDetails()
	{
		workOrderNo = "";
		workOrderId = 0;
		custName = "";
		jobName = "";
		drwgNo = "";
		rvsnNo = "";
		matlType = "";
		workOrderJbId = 0;
	}
	/**
	 * @return Returns the drwgNo.
	 */
	public String getDrwgNo() {
		return drwgNo;
	}
	/**
	 * @param drwgNo The drwgNo to set.
	 */
	public void setDrwgNo(String drwgNo) {
		this.drwgNo = drwgNo;
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
	 * @return Returns the matlType.
	 */
	public String getMatlType() {
		return matlType;
	}
	/**
	 * @param matlType The matlType to set.
	 */
	public void setMatlType(String matlType) {
		this.matlType = matlType;
	}
	/**
	 * @return Returns the rvsnNo.
	 */
	public String getRvsnNo() {
		return rvsnNo;
	}
	/**
	 * @param rvsnNo The rvsnNo to set.
	 */
	public void setRvsnNo(String rvsnNo) {
		this.rvsnNo = rvsnNo;
	}
	/**
	 * @return Returns the workOrderDate.
	 */
	public Date getWorkOrderDate() {
		return workOrderDate;
	}
	/**
	 * @param workOrderDate The workOrderDate to set.
	 */
	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}
	/**
	 * @return Returns the workOrderId.
	 */
	public int getWorkOrderId() {
		return workOrderId;
	}
	/**
	 * @param workOrderId The workOrderId to set.
	 */
	public void setWorkOrderId(int workOrderId) {
		this.workOrderId = workOrderId;
	}
	/**
	 * @return Returns the workOrderJbId.
	 */
	public int getWorkOrderJbId() {
		return workOrderJbId;
	}
	/**
	 * @param workOrderJbId The workOrderJbId to set.
	 */
	public void setWorkOrderJbId(int workOrderJbId) {
		this.workOrderJbId = workOrderJbId;
	}
	/**
	 * @return Returns the workOrderNo.
	 */
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	/**
	 * @param workOrderNo The workOrderNo to set.
	 */
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	/**
	 * @return Returns the custName.
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName The custName to set.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
}
/***
$Log: WorkOrderCloseDetails.java,v $
Revision 1.2  2004/12/09 05:54:46  kduraisamy
Log added.

***/