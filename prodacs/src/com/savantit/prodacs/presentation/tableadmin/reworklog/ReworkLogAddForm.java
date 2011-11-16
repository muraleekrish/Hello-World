/*
 * Created on Jan 5, 2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.savantit.prodacs.presentation.tableadmin.reworklog;

import org.apache.struts.action.ActionForm;

/**
 * @author vkrishnamoorthy
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReworkLogAddForm extends ActionForm 
{
	private String wrkOrdNum = "0";
	private String jobName = "0";
	private String reworkCategory = "0";
	private String reworkReason = "0";
	private String authorizedBy = "";
	private String formAction = ""; 
	private String woJbStatId = "0"; 
	private String rwkLogDetails = ""; 
	private String hidJbQtySno = ""; 
	private String jobs;
	
	/**
	 * @return Returns the jobs.
	 */
	public String getJobs() {
		return jobs;
	}
	/**
	 * @param jobs The jobs to set.
	 */
	public void setJobs(String jobs) {
		this.jobs = jobs;
	}
	/**
	 * @return Returns the rwkLogDetails.
	 */
	public String getRwkLogDetails() {
		return rwkLogDetails;
	}
	/**
	 * @param rwkLogDetails The rwkLogDetails to set.
	 */
	public void setRwkLogDetails(String rwkLogDetails) {
		this.rwkLogDetails = rwkLogDetails;
	}
	/**
	 * @return Returns the formAction.
	 */
	public String getFormAction() {
		return formAction;
	}
	/**
	 * @param formAction The formAction to set.
	 */
	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}
	/**
	 * @return Returns the reworkCategory.
	 */
	public String getReworkCategory() {
		return reworkCategory;
	}
	/**
	 * @param reworkCategory The reworkCategory to set.
	 */
	public void setReworkCategory(String reworkCategory) {
		this.reworkCategory = reworkCategory;
	}
	/**
	 * @return Returns the reworkReason.
	 */
	public String getReworkReason() {
		return reworkReason;
	}
	/**
	 * @param reworkReason The reworkReason to set.
	 */
	public void setReworkReason(String reworkReason) {
		this.reworkReason = reworkReason;
	}
	/**
	 * @return Returns the authorizedBy.
	 */
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	/**
	 * @param authorizedBy The authorizedBy to set.
	 */
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
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
	 * @return Returns the wrkOrdNum.
	 */
	public String getWrkOrdNum() {
		return wrkOrdNum;
	}
	/**
	 * @param wrkOrdNum The wrkOrdNum to set.
	 */
	public void setWrkOrdNum(String wrkOrdNum) {
		this.wrkOrdNum = wrkOrdNum;
	}
	
	/**
	 * @return Returns the woJbStatId.
	 */
	public String getWoJbStatId() {
		return woJbStatId;
	}
	/**
	 * @param woJbStatId The woJbStatId to set.
	 */
	public void setWoJbStatId(String woJbStatId) {
		this.woJbStatId = woJbStatId;
	}
	/**
	 * @return Returns the hidJbQtySno.
	 */
	public String getHidJbQtySno() {
		return hidJbQtySno;
	}
	/**
	 * @param hidJbQtySno The hidJbQtySno to set.
	 */
	public void setHidJbQtySno(String hidJbQtySno) {
		this.hidJbQtySno = hidJbQtySno;
	}
}

/***
$Log $
***/
