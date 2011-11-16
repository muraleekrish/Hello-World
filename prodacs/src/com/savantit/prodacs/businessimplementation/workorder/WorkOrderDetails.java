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
public class WorkOrderDetails implements Serializable 
{
	private Date woCreatedDate;
	private String woStatus;
	private Date woEstmdCompleteDate;
	private int custTypeId;
	private int custId;
	private String custTypName;
	private String custName;
	private String workOrderNo;
	private int workOrderId;
	private String contactPerson;
	private String workOrderStatus;
	private int workOrderIsValid;
	private Date workOrderDateStamp;
	private Vector WOJobDetails;
	
	public WorkOrderDetails()
	{
		this.woStatus = "";
		this.custTypeId = 0;
		this.custId = 0;
		this.custTypName = "";
		this.custName = "";
		this.workOrderNo = "";
		this.workOrderId = 0;
		this.workOrderIsValid = 0;
		this.contactPerson = "";
		this.workOrderStatus = "";
	}
	/**
	 * @return Returns the contactPerson.
	 */
	public String getContactPerson() {
		return contactPerson;
	}
	/**
	 * @param contactPerson The contactPerson to set.
	 */
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	/**
	 * @return Returns the custId.
	 */
	public int getCustId() {
		return custId;
	}
	/**
	 * @param custId The custId to set.
	 */
	public void setCustId(int custId) {
		this.custId = custId;
	}
	/**
	 * @return Returns the custTypeId.
	 */
	public int getCustTypeId() {
		return custTypeId;
	}
	/**
	 * @param custTypeId The custTypeId to set.
	 */
	public void setCustTypeId(int custTypeId) {
		this.custTypeId = custTypeId;
	}
	/**
	 * @return Returns the woCreatedDate.
	 */
	public Date getWoCreatedDate() {
		return woCreatedDate;
	}
	/**
	 * @param woCreatedDate The woCreatedDate to set.
	 */
	public void setWoCreatedDate(Date woCreatedDate) {
		this.woCreatedDate = woCreatedDate;
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
	 * @return Returns the woEstmdCompleteDate.
	 */
	public Date getWoEstmdCompleteDate() {
		return woEstmdCompleteDate;
	}
	/**
	 * @param woEstmdCompleteDate The woEstmdCompleteDate to set.
	 */
	public void setWoEstmdCompleteDate(Date woEstmdCompleteDate) {
		this.woEstmdCompleteDate = woEstmdCompleteDate;
	}
	/**
	 * @return Returns the wOJobDetails.
	 */
	public Vector getWOJobDetails() {
		return WOJobDetails;
	}
	/**
	 * @param jobDetails The wOJobDetails to set.
	 */
	public void setWOJobDetails(Vector jobDetails) {
		WOJobDetails = jobDetails;
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
	/**
	 * @param workOrderNo The workOrderNo to set.
	 */
	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}
	/**
	 * @return Returns the workOrderNo.
	 */
	public String getWorkOrderNo() {
		return workOrderNo;
	}
	/**
	 * @return Returns the custTypName.
	 */
	public String getCustTypName() {
		return custTypName;
	}
	/**
	 * @param custTypName The custTypName to set.
	 */
	public void setCustTypName(String custTypName) {
		this.custTypName = custTypName;
	}
	/**
	 * @return Returns the workOrderDateStamp.
	 */
	public Date getWorkOrderDateStamp() {
		return workOrderDateStamp;
	}
	/**
	 * @param workOrderDateStamp The workOrderDateStamp to set.
	 */
	public void setWorkOrderDateStamp(Date workOrderDateStamp) {
		this.workOrderDateStamp = workOrderDateStamp;
	}
	/**
	 * @return Returns the workOrderIsValid.
	 */
	public int getWorkOrderIsValid() {
		return workOrderIsValid;
	}
	/**
	 * @param workOrderIsValid The workOrderIsValid to set.
	 */
	public void setWorkOrderIsValid(int workOrderIsValid) {
		this.workOrderIsValid = workOrderIsValid;
	}
	/**
	 * @return Returns the workOrderStatus.
	 */
	public String getWorkOrderStatus() {
		return workOrderStatus;
	}
	/**
	 * @param workOrderStatus The workOrderStatus to set.
	 */
	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
    /**
     * @return Returns the woStatus.
     */
    public String getWoStatus()
    {
        return woStatus;
    }
    /**
     * @param woStatus The woStatus to set.
     */
    public void setWoStatus(String woStatus)
    {
        this.woStatus = woStatus;
    }
}
/***
$Log: WorkOrderDetails.java,v $
Revision 1.7  2005/08/09 04:54:31  kduraisamy
statuses added in wo view.

Revision 1.6  2004/12/09 05:54:46  kduraisamy
Log added.

***/