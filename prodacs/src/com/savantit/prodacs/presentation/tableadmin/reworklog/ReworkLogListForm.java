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
public class ReworkLogListForm extends ActionForm
{
	private String rwkLogFromDate = "";
	private String rwkLogToDate = "";
	private String listJobName = "";
	private String jobSoryBy = "0";
	private String listValidEntries = "0";
	
	private String formAction="";
	private String maxItems="15";
	private String page="1";
	private String sortField="RWK_CATEGORY";
	private String sortOrder="ascending";   	

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
	 * @return Returns the maxItems.
	 */
	public String getMaxItems() {
		return maxItems;
	}
	/**
	 * @param maxItems The maxItems to set.
	 */
	public void setMaxItems(String maxItems) {
		this.maxItems = maxItems;
	}
	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}
	/**
	 * @param page The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}
	/**
	 * @return Returns the sortField.
	 */
	public String getSortField() {
		return sortField;
	}
	/**
	 * @param sortField The sortField to set.
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	/**
	 * @return Returns the sortOrder.
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder The sortOrder to set.
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return Returns the rwkLogFromDate.
	 */
	public String getRwkLogFromDate() {
		return rwkLogFromDate;
	}
	/**
	 * @param rwkLogFromDate The rwkLogFromDate to set.
	 */
	public void setRwkLogFromDate(String rwkLogFromDate) {
		this.rwkLogFromDate = rwkLogFromDate;
	}
	/**
	 * @return Returns the rwkLogToDate.
	 */
	public String getRwkLogToDate() {
		return rwkLogToDate;
	}
	/**
	 * @param rwkLogToDate The rwkLogToDate to set.
	 */
	public void setRwkLogToDate(String rwkLogToDate) {
		this.rwkLogToDate = rwkLogToDate;
	}
	/**
	 * @return Returns the listJobName.
	 */
	public String getListJobName() {
		return listJobName;
	}
	/**
	 * @param listJobName The listJobName to set.
	 */
	public void setListJobName(String listJobName) {
		this.listJobName = listJobName;
	}
	/**
	 * @return Returns the jobSoryBy.
	 */
	public String getJobSoryBy() {
		return jobSoryBy;
	}
	/**
	 * @param jobSoryBy The jobSoryBy to set.
	 */
	public void setJobSoryBy(String jobSoryBy) {
		this.jobSoryBy = jobSoryBy;
	}
	/**
	 * @return Returns the listValidEntries.
	 */
	public String getListValidEntries() {
		return listValidEntries;
	}
	/**
	 * @param listValidEntries The listValidEntries to set.
	 */
	public void setListValidEntries(String listValidEntries) {
		this.listValidEntries = listValidEntries;
	}
}


/***
$Log: ReworkLogListForm.java,v $
Revision 1.3  2005/02/03 05:13:35  vkrishnamoorthy
Log added.

***/