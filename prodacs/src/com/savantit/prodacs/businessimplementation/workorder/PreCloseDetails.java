/*
 * Created on Dec 8, 2004
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
public class PreCloseDetails implements Serializable
{
	private int workOrderId;
	private String workOrderNo;
	private Date workOrderDate;
	private String jbName;
	private String jbDwgNo;
	private String jbRvsnNo;
	private String jbMatlTyp;
	private int jbSno;
	private int jbId;
	private int workOrderJbStatId;
	private int precloseLogId;
	private int workOrderJbStatSno;
	private String preCloseReason;
	private Date preCloseLogDate;
	private int preCloseIsValid;
	private Date preCloseDateStamp;
	private Vector JobQtyDetails;
	public PreCloseDetails()
	{
		workOrderId = 0;
		workOrderNo = "";
		workOrderJbStatId = 0;
		workOrderJbStatSno = 0;
		precloseLogId = 0;
		jbName = "";
		jbDwgNo = "";
		jbRvsnNo = "";
		jbMatlTyp = "";
		jbSno = 0;
		jbId = 0;
		preCloseReason = "";
		preCloseIsValid = 0;
		
	}
	
	/**
	 * @return Returns the jbDwgNo.
	 */
	public String getJbDwgNo() {
		return jbDwgNo;
	}
	/**
	 * @param jbDwgNo The jbDwgNo to set.
	 */
	public void setJbDwgNo(String jbDwgNo) {
		this.jbDwgNo = jbDwgNo;
	}
	/**
	 * @return Returns the jbId.
	 */
	public int getJbId() {
		return jbId;
	}
	/**
	 * @param jbId The jbId to set.
	 */
	public void setJbId(int jbId) {
		this.jbId = jbId;
	}
	/**
	 * @return Returns the jbMatlTyp.
	 */
	public String getJbMatlTyp() {
		return jbMatlTyp;
	}
	/**
	 * @param jbMatlTyp The jbMatlTyp to set.
	 */
	public void setJbMatlTyp(String jbMatlTyp) {
		this.jbMatlTyp = jbMatlTyp;
	}
	/**
	 * @return Returns the jbName.
	 */
	public String getJbName() {
		return jbName;
	}
	/**
	 * @param jbName The jbName to set.
	 */
	public void setJbName(String jbName) {
		this.jbName = jbName;
	}
	/**
	 * @return Returns the jbRvsnNo.
	 */
	public String getJbRvsnNo() {
		return jbRvsnNo;
	}
	/**
	 * @param jbRvsnNo The jbRvsnNo to set.
	 */
	public void setJbRvsnNo(String jbRvsnNo) {
		this.jbRvsnNo = jbRvsnNo;
	}
	/**
	 * @return Returns the preCloseReason.
	 */
	public String getPreCloseReason() {
		return preCloseReason;
	}
	/**
	 * @param preCloseReason The preCloseReason to set.
	 */
	public void setPreCloseReason(String preCloseReason) {
		this.preCloseReason = preCloseReason;
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
	 * @return Returns the wOJobDetails.
	 */
	public Vector getJobQtyDetails() {
		return JobQtyDetails;
	}
	/**
	 * @param jobDetails The wOJobDetails to set.
	 */
	public void setJobQtyDetails(Vector jobQtyDetails) {
		JobQtyDetails = jobQtyDetails;
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
	 * @return Returns the workOrderJbStatId.
	 */
	public int getWorkOrderJbStatId() {
		return workOrderJbStatId;
	}
	/**
	 * @param workOrderJbStatId The workOrderJbStatId to set.
	 */
	public void setWorkOrderJbStatId(int workOrderJbStatId) {
		this.workOrderJbStatId = workOrderJbStatId;
	}
	/**
	 * @return Returns the workOrderJbStatSno.
	 */
	public int getWorkOrderJbStatSno() {
		return workOrderJbStatSno;
	}
	/**
	 * @param workOrderJbStatSno The workOrderJbStatSno to set.
	 */
	public void setWorkOrderJbStatSno(int workOrderJbStatSno) {
		this.workOrderJbStatSno = workOrderJbStatSno;
	}
	/**
	 * @return Returns the preCloseDateStamp.
	 */
	public Date getPreCloseDateStamp() {
		return preCloseDateStamp;
	}
	/**
	 * @param preCloseDateStamp The preCloseDateStamp to set.
	 */
	public void setPreCloseDateStamp(Date preCloseDateStamp) {
		this.preCloseDateStamp = preCloseDateStamp;
	}
	/**
	 * @return Returns the preCloseIsValid.
	 */
	public int getPreCloseIsValid() {
		return preCloseIsValid;
	}
	/**
	 * @param preCloseIsValid The preCloseIsValid to set.
	 */
	public void setPreCloseIsValid(int preCloseIsValid) {
		this.preCloseIsValid = preCloseIsValid;
	}
	/**
	 * @return Returns the preCloseLogDate.
	 */
	public Date getPreCloseLogDate() {
		return preCloseLogDate;
	}
	/**
	 * @param preCloseLogDate The preCloseLogDate to set.
	 */
	public void setPreCloseLogDate(Date preCloseLogDate) {
		this.preCloseLogDate = preCloseLogDate;
	}
	/**
	 * @return Returns the jbSno.
	 */
	public int getJbSno() {
		return jbSno;
	}
	/**
	 * @param jbSno The jbSno to set.
	 */
	public void setJbSno(int jbSno) {
		this.jbSno = jbSno;
	}
    /**
     * @return Returns the precloseLogId.
     */
    public int getPrecloseLogId()
    {
        return precloseLogId;
    }
    /**
     * @param precloseLogId The precloseLogId to set.
     */
    public void setPrecloseLogId(int precloseLogId)
    {
        this.precloseLogId = precloseLogId;
    }
}
/***
$Log: PreCloseDetails.java,v $
Revision 1.5  2005/02/02 14:38:54  kduraisamy
FIELD PRECLOSELOGID ADDED IN VALUE OBJECT.

Revision 1.4  2004/12/17 09:13:25  sduraisamy
PreCloseLogDate included

Revision 1.3  2004/12/09 05:55:02  kduraisamy
Log added.

***/
